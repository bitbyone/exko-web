package io.exko.stimulus.spring

import io.exko.stimulus.ControllerConfig
import io.exko.stimulus.StimulusController
import java.security.MessageDigest

class StimulusBundle(controllers: List<StimulusController>) {

    data class Entry(
        val name: String,
        val content: String,
        val hash: String,
        val filename: String,
    )

    val entries: List<Entry>
    val mainContent: String
    val mainHash: String
    val mainPath: String

    val fileMap: Map<String, String>

    init {
        entries = controllers.sortedBy { it.name }.map { ctrl ->
            val content = ctrl.code(ControllerConfig(ctrl.name)).trimIndent() + "\n"
            val hash = sha256(content).take(8)
            Entry(
                name = ctrl.name,
                content = content,
                hash = hash,
                filename = "${ctrl.name}-$hash",
            )
        }

        mainContent = buildString {
            appendLine("""import { Application } from "@hotwired/stimulus"""")
            entries.forEach { entry ->
                val className = kebabToPascalCase(entry.name) + "Controller"
                appendLine("""import $className from "/__stimulus/${entry.filename}.js"""")
            }
            appendLine()
            appendLine("window.Stimulus = Application.start()")
            appendLine()
            entries.forEach { entry ->
                val className = kebabToPascalCase(entry.name) + "Controller"
                appendLine("""Stimulus.register("${entry.name}", $className)""")
            }
        }
        mainHash = sha256(mainContent).take(8)
        mainPath = "/__stimulus/stimulus-$mainHash.js"

        val controllerEntries = entries
        fileMap = buildMap {
            put("stimulus-$mainHash", mainContent)
            controllerEntries.forEach { entry -> put(entry.filename, entry.content) }
        }
    }
}

private fun sha256(input: String): String {
    val digest = MessageDigest.getInstance("SHA-256")
    return digest.digest(input.toByteArray()).joinToString("") { "%02x".format(it) }
}

private fun kebabToPascalCase(kebab: String): String =
    kebab.split("-").joinToString("") { part ->
        part.replaceFirstChar { it.uppercaseChar() }
    }
