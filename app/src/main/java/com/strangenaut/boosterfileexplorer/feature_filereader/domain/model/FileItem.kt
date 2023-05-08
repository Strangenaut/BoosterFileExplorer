package com.strangenaut.boosterfileexplorer.feature_filereader.domain.model

import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes
import java.util.Date

data class FileItem(
    val path: String,
    val iconId: Int?,
    val name: String = File(path).name,
    val isDirectory: Boolean = File(path).isDirectory,
    val sizeBytes: Long = if (isDirectory) 0 else File(path).length(),
    val extension: String = if (isDirectory)
            ""
        else if (File(path).extension != "")
            File(path).extension
        else
            "file",
    var hasChangedSinceLastLaunch: Boolean = false
) {
    val sizeFormatted: String
        get() {
            if (isDirectory) {
                return ""
            }

            var size = sizeBytes.toDouble()
            var rank = 0

            while(size > 1024) {
                size /= 1024.0
                rank++
            }

            return if (size - size.toInt() >= 0.01)
                "${String.format("%.2f", size)} ${SizeUnit.values()[rank]}"
            else
                "${size.toInt()} ${SizeUnit.values()[rank]}"
        }

    val creationDateTime: Date
        get() {
            val fileAttr = Files.readAttributes(File(path).toPath(), BasicFileAttributes::class.java)
            return Date.from(fileAttr.creationTime().toInstant()) as Date
        }

    val parentPath: String?
        get() { return File(path).parent }

    val fileHashCode: Int
        get() { return sizeBytes.hashCode() }

    enum class SizeUnit {
        B,
        KB,
        MB,
        GB,
        TB
    }
}