package com.strangenaut.boosterfileexplorer.feature_filereader.presentation.fileslist

import com.strangenaut.boosterfileexplorer.feature_filereader.data.model.FileHashInfo
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.model.FileItem
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.util.FileOrder
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.util.OrderType

data class FileListState (
    val currentFile: FileItem = FileItem(path = "", iconId = null),
    val nestedFiles: List<FileItem> = listOf(),
    val previousNestedFilesHashInfoList: List<FileHashInfo> = listOf(),
    val fileOrder: FileOrder = FileOrder.TitleAndExtension(OrderType.Ascending),
    val isOrderSectionVisible: Boolean = false
)