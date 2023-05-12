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
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.model.FileItem
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.usecase.FileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
                val file = File(event.path ?: return)

                if (file.isFile) {
                    openFileUsingOtherApp(file, Intent.ACTION_VIEW, event.context)
                    return
                }

                _state.value = _state.value.copy(
                    currentFile = FileItem(event.path),
                    nestedFiles = useCases.getNestedFileItems(event.path)
                )
            }
            is FileListEvent.ShareFile -> {
                val file = File(event.path ?: return)

                openFileUsingOtherApp(file, Intent.ACTION_SEND, event.context)
            }
            is FileListEvent.Order -> {
                viewModelScope.launch(Dispatchers.Default) {
                    _state.value = _state.value.copy(
                        nestedFiles = useCases.orderFiles(
                            event.fileOrder,
                            _state.value.nestedFiles
                        ),
                        fileOrder = event.fileOrder
                    )
                }
            }
            is FileListEvent.GetFileHashInfoList -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.value = _state.value.copy(
                        previousNestedFilesHashInfoList = useCases.getFileHashInfoList()
                    )
                }
            }
            is FileListEvent.InsertNestedFilesHashInfoList -> {
                viewModelScope.launch(Dispatchers.Default) {
                    useCases.insertNestedFilesHashInfoList(event.path ?: return@launch)
                }
            }
            is FileListEvent.CompareFiles -> {
                viewModelScope.launch(Dispatchers.Default) {
                    useCases.compareFiles(
                        files = _state.value.nestedFiles,
                        hashInfoList =  _state.value.previousNestedFilesHashInfoList
                    )
                }
            }
            is FileListEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
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
    }
}