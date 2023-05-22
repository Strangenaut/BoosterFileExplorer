package com.strangenaut.boosterfileexplorer.feature_filereader.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.model.FileHashInfo

@Database(
    entities = [FileHashInfo::class],
    version = 1
)
abstract class FileDatabase: RoomDatabase() {

    abstract val fileDao: FileDao

    companion object {
        const val DATABASE_NAME = "fileHashInfo_db"
    }
}