package com.strangenaut.boosterfileexplorer.feature_filereader.domain.usecase

import com.strangenaut.boosterfileexplorer.feature_filereader.domain.model.FileItem
import java.io.File

class GetNestedFileItems {

    operator fun invoke(path: String): List<FileItem> {
        val fileItemsList = mutableListOf<FileItem>()

        File(path).listFiles()?.forEach {
            val fileItem = FileItem(it.absolutePath)
            fileItemsList.add(fileItem)
        }
        return fileItemsList
    }
}