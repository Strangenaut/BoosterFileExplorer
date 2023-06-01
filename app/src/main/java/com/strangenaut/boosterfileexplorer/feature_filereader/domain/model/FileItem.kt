package com.strangenaut.boosterfileexplorer.feature_filereader.domain.model

import com.strangenaut.boosterfileexplorer.R
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes
import java.security.MessageDigest
import java.util.Date

data class FileItem(
    val path: String,
    val name: String = File(path).name,
    val isDirectory: Boolean = File(path).isDirectory,
    val sizeBytes: Long = if (isDirectory) 0 else File(path).length(),
    val extension: String =
        if (isDirectory)
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

    val fileHashCode: String
        get() { return File(path).calculateHash() }

    val iconId: Int
        get() {
            val file = File(path)

            if (file.isDirectory) {
                return R.drawable.folder
            }

            return when (file.extension) {
                "png", "jpg", "jpeg", "gif", "bmp" -> R.drawable.image
                "mp3", "wav", "ogg", "midi" -> R.drawable.audio
                "mp4", "rmvb", "avi", "flv", "3gp" -> R.drawable.video
                "c", ".cpp", "xml", "py", "json", "js", "php", "jsp", "html", "htm" -> R.drawable.code
                "txt", "log" -> R.drawable.text
                "xls", "xlsx" -> R.drawable.table
                "doc", "docx" -> R.drawable.document
                "pdf" -> R.drawable.pdf
                "jar", "zip", "rar", "gz" -> R.drawable.zip
                "apk" -> R.drawable.apk
                else -> R.drawable.file
            }
        }

    enum class SizeUnit {
        B,
        KB,
        MB,
        GB,
        TB
    }
}

fun File.calculateHash(): String {
    val file = File(path)
    val md = MessageDigest.getInstance("SHA-256")
    file.inputStream().use { input ->
        val buffer = ByteArray(8192)
        var bytesRead = input.read(buffer)

        while (bytesRead != -1) {
            md.update(buffer, 0, bytesRead)
            bytesRead = input.read(buffer)
        }
    }
    val hashBytes = md.digest()
    return hashBytes.joinToString("") { "%02x".format(it) }
}