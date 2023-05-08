package com.strangenaut.boosterfileexplorer.feature_filereader.presentation.fileslist

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strangenaut.boosterfileexplorer.BuildConfig
import com.strangenaut.boosterfileexplorer.R
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.model.FileItem
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.usecase.FileUseCases
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.util.FileOrder
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FilesListViewModel @Inject constructor(
    private val useCases: FileUseCases
) : ViewModel() {

    private val _state = mutableStateOf(FileListState())
    val state: State<FileListState> = _state

    fun onEvent(event: FileListEvent) {
        when (event) {
            is FileListEvent.OpenFile -> {
                if (event.path == null) {
                    return
                }

                val file = File(event.path)

                if (file.isFile) {
                    openFileUsingOtherApp(file, Intent.ACTION_VIEW, event.context)
                    return
                }

                val rootFileItem = buildFileItem(event.path)
                val fileItemsList = mutableListOf<FileItem>()

                File(event.path).listFiles()?.forEach {
                    val fileItem = buildFileItem(it.absolutePath)
                    fileItemsList.add(fileItem)
                }

                _state.value = _state.value.copy(
                    currentFile = rootFileItem,
                    nestedFiles = fileItemsList
                )
            }
            is FileListEvent.ShareFile -> {
                if (event.path == null) {
                    return
                }

                val file = File(event.path)
                openFileUsingOtherApp(file, Intent.ACTION_SEND, event.context)
            }
            is FileListEvent.Order -> {
                if (state.value.nestedFiles.isEmpty()) {
                    return
                }

                val order = event.fileOrder
                var filesList = _state.value.nestedFiles

                _state.value = _state.value.copy(
                    fileOrder = order
                )

                viewModelScope.launch(Dispatchers.Default) {
                    filesList = when(order.orderType) {
                        is OrderType.Ascending -> {
                            when(order) {
                                is FileOrder.TitleAndExtension -> filesList.sortedBy {
                                    it.name.lowercase(Locale.ROOT)
                                }.sortedBy {
                                    it.extension.lowercase(Locale.ROOT)
                                }
                                is FileOrder.Title -> filesList.sortedBy {
                                    it.name.lowercase(Locale.ROOT)
                                }
                                is FileOrder.Size -> filesList.sortedBy {
                                    it.sizeBytes
                                }
                                is FileOrder.Date -> filesList.sortedBy {
                                    it.creationDateTime
                                }
                                is FileOrder.Extension -> filesList.sortedBy {
                                    it.extension.lowercase(Locale.ROOT)
                                }
                            }
                        }
                        is OrderType.Descending -> {
                            when(order) {
                                is FileOrder.TitleAndExtension -> filesList.sortedByDescending {
                                    it.name.lowercase(Locale.ROOT)
                                }.sortedByDescending {
                                    it.extension.lowercase(Locale.ROOT)
                                }
                                is FileOrder.Title -> filesList.sortedByDescending {
                                    it.name.lowercase(Locale.ROOT)
                                }
                                is FileOrder.Size -> filesList.sortedByDescending {
                                    it.sizeBytes
                                }
                                is FileOrder.Date -> filesList.sortedByDescending {
                                    it.creationDateTime
                                }
                                is FileOrder.Extension -> filesList.sortedByDescending {
                                    it.extension.lowercase(Locale.ROOT)
                                }
                            }
                        }
                    }
                    _state.value = _state.value.copy(
                        nestedFiles = filesList
                    )
                }
            }
            is FileListEvent.UploadFileHashInfoList -> {
                if (event.path == null) {
                    return
                }

                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        _state.value = _state.value.copy(
                            previousNestedFilesHashInfoList = useCases.getFileHashInfoList()
                        )
                    }

                    withContext(Dispatchers.Default) {
                        useCases.insertFileHashInfoList(event.path)
                    }
                }
            }
            is FileListEvent.CompareFiles -> {
                viewModelScope.launch(Dispatchers.IO) {
                    for (file in _state.value.nestedFiles) {
                        val hashInfo = _state.value.previousNestedFilesHashInfoList.find {
                            file.path == it.path
                        }

                        if (hashInfo != null) {
                            file.hasChangedSinceLastLaunch =
                                file.fileHashCode != hashInfo.fileHashCode
                        }
                    }
                }
            }
            is FileListEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun buildFileItem(path: String): FileItem {
        return FileItem(
            path = path,
            iconId = getFileTypeIcon(path)
        )
    }

    private fun getFileTypeIcon(path: String): Int {
        val file = File(path)

        if (file.isDirectory) {
            return R.drawable.folder
        }

        return when (file.extension) {
            "png", "jpg", "jpeg", "gif", "bmp" -> R.drawable.image
            "mp3", "wav", "ogg", "midi" -> R.drawable.audio
            "mp4", "rmvb", "avi", "flv", "3gp" -> R.drawable.video
            "c", ".cpp", "xml", "py", "json", "js", "php", "jsp", "html", "htm" -> R.drawable.code
            "txt", "log" -> R.drawable.text
            "xls", "xlsx" -> R.drawable.table
            "doc", "docx" -> R.drawable.document
            "pdf" -> R.drawable.pdf
            "jar", "zip", "rar", "gz" -> R.drawable.zip
            "apk" -> R.drawable.apk
            else -> R.drawable.file
        }
    }

    private fun openFileUsingOtherApp(file: File, action: String, context: Context) {
        val uri = FileProvider.getUriForFile(
            Objects.requireNonNull(context),
            "${BuildConfig.APPLICATION_ID}.provider",
            file
        )
        val mime = context.contentResolver.getType(uri)

        val intent = Intent()
            .setAction(action)
            .setDataAndType(uri, mime)
            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        val chooser = Intent.createChooser(intent, null)

        try {
            context.startActivity(chooser)
        } catch (e: ActivityNotFoundException) {
            Log.e("Error: ", e.message.toString())
        }
        return
    }
}