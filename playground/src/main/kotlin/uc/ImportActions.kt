package io.exko.sandbox.uikit.uc

import io.exko.html.*
import io.exko.htmx.dsl.encodingMultipart
import io.exko.htmx.dsl.hx
import io.exko.sandbox.uikit.PlaygroundLayout
import io.exko.webawesome.components.button.Button
import io.exko.webawesome.components.card.Card
import io.exko.webawesome.components.fileinput.FileInput
import io.exko.webawesome.props.ButtonType
import kotlinx.html.*
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

// --- Data model ---

data class ImportRecord(
    val id: UUID,
    val originalFilename: String,
    val storedPath: String,
    val contentType: String?,
    val size: Long,
    val uploadedAt: LocalDateTime,
)

// --- Service ---

@Service
class ImportService {

    private val records = mutableListOf<ImportRecord>()
    private val uploadDir: Path = Path.of("uploads/imports")

    fun saveFiles(files: List<MultipartFile>): List<ImportRecord> {
        Files.createDirectories(uploadDir)

        return files
            .filter { !it.isEmpty }
            .map { file ->
                val id = UUID.randomUUID()
                val safeName = file.originalFilename
                    ?.replace(Regex("[^a-zA-Z0-9._-]"), "_")
                    ?: "file"
                val storedFilename = "${id}_${safeName}"
                val targetPath = uploadDir.resolve(storedFilename)
                file.transferTo(targetPath)

                ImportRecord(
                    id = id,
                    originalFilename = file.originalFilename ?: "unknown",
                    storedPath = targetPath.toAbsolutePath().toString(),
                    contentType = file.contentType,
                    size = file.size,
                    uploadedAt = LocalDateTime.now(),
                ).also { records.add(it) }
            }
    }

    fun getAll(): List<ImportRecord> = records.toList()
}

// --- Controller ---

@Controller
@RequestMapping("/uc/imports")
class ImportController(private val importService: ImportService) {

    @GetMapping
    fun renderPage() = view(page = ImportsPage(importService.getAll()), layout = ::PlaygroundLayout)

    @PostMapping(consumes = ["multipart/form-data"])
    fun handleUpload(
        @RequestParam("files") files: List<MultipartFile>,
    ): Render {
        importService.saveFiles(files)
        return view(page = ImportsList(importService.getAll()))
    }
}

// --- Views ---

@UI
fun ImportsPage(records: List<ImportRecord>) = fragment {
    h1 { +"File Import" }

    Card {
        slotHeader { span("wa-heading-s") { +"Upload Files" } }

        form {
            id = "import-form"
            hx {
                +("post" to "/uc/imports")
                +("target" to "#imports-list")
                +("swap" to "outerHTML")
                encodingMultipart()
            }

            FileInput(
                name = "files",
                label = "Select files to import",
                multiple = true,
            )

            br

            Button(type = ButtonType.submit) { +"Upload" }
        }
    }

    br

    ImportsList(records)
}

@UI
fun ImportsList(records: List<ImportRecord>) = fragment {
    div {
        id = "imports-list"

        h2 { +"Imported Files (${records.size})" }

        if (records.isEmpty()) {
            p { +"No files imported yet." }
        } else {
            table {
                style = "width: 100%; border-collapse: collapse;"
                thead {
                    tr {
                        th { +"Filename" }
                        th { +"Type" }
                        th { +"Size" }
                        th { +"Uploaded" }
                        th { +"Stored Path" }
                    }
                }
                tbody {
                    records.sortedByDescending { it.uploadedAt }.forEach { record ->
                        tr {
                            td { +record.originalFilename }
                            td { +(record.contentType ?: "-") }
                            td { +formatSize(record.size) }
                            td { +record.uploadedAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) }
                            td {
                                code {
                                    style = "font-size: 0.75em; word-break: break-all;"
                                    +record.storedPath
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun formatSize(bytes: Long): String = when {
    bytes < 1024 -> "$bytes B"
    bytes < 1024 * 1024 -> "${"%.1f".format(bytes / 1024.0)} KB"
    else -> "${"%.1f".format(bytes / (1024.0 * 1024))} MB"
}
