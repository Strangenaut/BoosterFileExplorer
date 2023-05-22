package com.strangenaut.boosterfileexplorer.feature_filereader.data.repository

import com.strangenaut.boosterfileexplorer.feature_filereader.data.datasource.FileDao
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.model.FileHashInfo
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.repository.FileRepository

class FileRepositoryImpl(
    private val dao: FileDao
) : FileRepository {

    override suspend fun getFileHashInfoList(): List<FileHashInfo> {
        return dao.getFileHashInfoList()
    }

    override suspend fun insertFileHashInfoList(fileHashInfoList: List<FileHashInfo>) {
        dao.clearTable()
        fileHashInfoList.forEach {
            dao.insertFileHashInfo(it)
        }
    }
}