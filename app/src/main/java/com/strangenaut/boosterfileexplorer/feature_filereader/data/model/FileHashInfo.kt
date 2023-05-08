package com.strangenaut.boosterfileexplorer.feature_filereader.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FileHashInfo(
    @PrimaryKey val path: String,
    val fileHashCode: Int
)