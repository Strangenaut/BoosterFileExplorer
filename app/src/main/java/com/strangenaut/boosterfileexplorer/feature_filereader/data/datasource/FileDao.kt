package com.strangenaut.boosterfileexplorer.feature_filereader.data.datasource

import androidx.room.*
import com.strangenaut.boosterfileexplorer.feature_filereader.data.model.FileHashInfo

@Dao
interface FileDao {

    @Query("SELECT * FROM fileHashInfo")
    suspend fun getFileHashInfoList(): List<FileHashInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFileHashInfo(fileHashInfo: FileHashInfo)

    @Query("DELETE FROM fileHashInfo")
    suspend fun clearTable()
}