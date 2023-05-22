package com.strangenaut.boosterfileexplorer.feature_filereader.domain.usecase

import com.strangenaut.boosterfileexplorer.feature_filereader.domain.model.FileHashInfo
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.repository.FileRepository

class GetFileHashInfoList(
    private val repository: FileRepository
) {

    suspend operator fun invoke(): List<FileHashInfo> {
        return repository.getFileHashInfoList()
    }
}