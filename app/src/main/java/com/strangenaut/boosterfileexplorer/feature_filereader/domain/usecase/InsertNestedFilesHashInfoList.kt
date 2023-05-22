package com.strangenaut.boosterfileexplorer.feature_filereader.domain.usecase

import com.strangenaut.boosterfileexplorer.feature_filereader.domain.model.FileHashInfo
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.repository.FileRepository
import java.io.File

class InsertNestedFilesHashInfoList(
    private val repository: FileRepository
) {

    suspend operator fun invoke(path: String) {
        val file = File(path)
        val fileTree = file.walk(direction = FileWalkDirection.TOP_DOWN)
        val fileHashInfoList = mutableListOf<FileHashInfo>()

        fileTree.forEach {
            if (it.isFile) {
                val info = FileHashInfo(it.path, it.length().hashCode())
                fileHashInfoList.add(info)
            }
        }
        repository.insertFileHashInfoList(fileHashInfoList)
    }
}