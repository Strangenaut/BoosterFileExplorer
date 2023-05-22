package com.strangenaut.boosterfileexplorer.feature_filereader.domain.repository

import com.strangenaut.boosterfileexplorer.feature_filereader.domain.model.FileHashInfo

interface FileRepository {

    suspend fun getFileHashInfoList(): List<FileHashInfo>

    suspend fun insertFileHashInfoList(fileHashInfoList: List<FileHashInfo>)
}