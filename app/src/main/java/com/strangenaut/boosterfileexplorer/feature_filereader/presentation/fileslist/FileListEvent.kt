package com.strangenaut.boosterfileexplorer.feature_filereader.presentation.fileslist

import android.content.Context
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.util.FileOrder

sealed class FileListEvent {
    data class OpenFile(val path: String?, val context: Context): FileListEvent()
    data class ShareFile(val path: String?, val context: Context): FileListEvent()
    data class Order(val fileOrder: FileOrder): FileListEvent()
    object GetFileHashInfoList: FileListEvent()
    data class InsertNestedFilesHashInfoList(val path: String?): FileListEvent()
    object CompareFiles: FileListEvent()
    object ToggleOrderSection: FileListEvent()
}