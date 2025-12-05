package io.exko.spring.hotswap

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.*
import org.springframework.beans.factory.getBean
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.devtools.autoconfigure.ConditionalOnEnabledDevTools
import org.springframework.context.event.EventListener
import java.nio.file.*
import java.nio.file.StandardWatchEventKinds.*
import java.nio.file.attribute.BasicFileAttributes
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.io.path.exists

private val log = KotlinLogging.logger { }

private const val EVENTS_DEBOUNCE_INTERVAL_MS = 180L
private const val POLL_INTERVAL_MS = 80L

@ConditionalOnEnabledDevTools
@EnableConfigurationProperties(HotSwapAgentConfig::class)
@AutoConfiguration
class HotSwapAgentAutoConfiguration(val config: HotSwapAgentConfig) {

    private val supervisor by lazy {
        CoroutineScope(Dispatchers.IO + SupervisorJob())
    }

    @EventListener
    fun afterStartup(e: ApplicationStartedEvent) {
        if (!config.enabled) return
        val appContext = e.applicationContext
        val webProps = appContext.getBean<WebProperties>()

        val livereloadServerClazz = runCatching {
            Class.forName("org.springframework.boot.devtools.livereload.LiveReloadServer")
        }.getOrElse { return }
        val livereloadServerBean = runCatching { appContext.getBean(livereloadServerClazz) }.getOrElse { return }

        val isStartedMethod = livereloadServerClazz.getMethod("isStarted")
        val isStarted = isStartedMethod.invoke(livereloadServerBean) as Boolean
        if (isStarted) {
            HotSwapAgentUtils.isLivereloadServerStarted.set(true)
            log.info { "[LiveReload] server started" }
        }

        runCatching { Class.forName("org.hotswap.agent.HotswapAgent") }.getOrElse { return }
        log.info { "[LiveReload] active in cooperation with HotswapAgent" }

        supervisor.launch {
            var watchService: WatchService? = null
            try {
                watchService = FileSystems.getDefault().newWatchService()
                config.watchBaseDirs.plus(Paths.get("./")).forEach { watchDir ->
                    val path = watchDir.resolve("build/classes/kotlin/main").toAbsolutePath()
                    if (path.exists()) {
                        watchFileTree(path, watchService, ENTRY_CREATE, ENTRY_MODIFY)
                        log.info { "[LiveReload] watching build files: $path" }
                    }
                }
                for (pathStr in webProps.resources.staticLocations) {
                    val res = appContext.getResource(pathStr)
                    if (!res.exists()) continue
                    val staticPath = res.file.toPath().toAbsolutePath()
                    if (!Files.exists(staticPath)) continue
                    watchFileTree(staticPath, watchService, ENTRY_CREATE, ENTRY_MODIFY)
                    log.info { "[LiveReload] watching static location: $staticPath" }
                }
                while (true) {
                    val key = watchService.poll()
                    if (key == null) {
                        delay(POLL_INTERVAL_MS)
                        continue
                    }
                    // now debounce multiple events in short succession
                    val keys = mutableSetOf(key)
                    val endTime = System.currentTimeMillis() + EVENTS_DEBOUNCE_INTERVAL_MS
                    while (System.currentTimeMillis() < endTime) {
                        watchService.poll()?.let { keys.add(it) }
                    }
                    keys.forEach {
                        for (event in it.pollEvents()) {
                            log.debug {
                                "[LiveReload] file ${event.kind().format()}: ${(event.context() as Path).fileName}"
                            }
                        }
                        it.reset()
                    }
                    log.info { "[LiveReload] reloading now..." }
                    val trigger = livereloadServerClazz.getMethod("triggerReload")
                    trigger.invoke(livereloadServerBean)
                }
            } catch (e: Exception) {
                log.error(e) { "[LiveReload] error" }
            } finally {
                withContext(NonCancellable) {
                    watchService?.close()
                }
            }
        }
    }

    private fun watchFileTree(
        path: Path,
        watchService: WatchService,
        vararg events: WatchEvent.Kind<*> = arrayOf(ENTRY_MODIFY),
    ) {
        Files.walkFileTree(
            path,
            object : SimpleFileVisitor<Path?>() {
                override fun preVisitDirectory(dir: Path?, attrs: BasicFileAttributes): FileVisitResult {
                    dir?.register(watchService, events)
                    return FileVisitResult.CONTINUE
                }
            },
        )
    }
}

private fun WatchEvent.Kind<*>.format(): String {
    return when (this) {
        ENTRY_CREATE -> "created"
        ENTRY_MODIFY -> "modified"
        ENTRY_DELETE -> "deleted"
        else -> "unknown"
    }
}

object HotSwapAgentUtils {
    val isLivereloadServerStarted: AtomicBoolean = AtomicBoolean(false)
}

@ConfigurationProperties(prefix = "hotswap.agent.livereload")
data class HotSwapAgentConfig(
    val enabled: Boolean = true,
    val watchBaseDirs: List<Path> = emptyList(),
)
