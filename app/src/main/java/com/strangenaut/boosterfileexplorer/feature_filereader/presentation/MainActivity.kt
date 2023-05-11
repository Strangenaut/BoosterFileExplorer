package com.strangenaut.boosterfileexplorer.feature_filereader.presentation

import android.Manifest
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import com.strangenaut.boosterfileexplorer.feature_filereader.presentation.fileslist.FileListEvent
import com.strangenaut.boosterfileexplorer.feature_filereader.presentation.fileslist.FilesListViewModel
import com.strangenaut.boosterfileexplorer.feature_filereader.presentation.fileslist.FilesListScreen
import com.strangenaut.boosterfileexplorer.ui.theme.BoosterFileExplorerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: FilesListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoosterFileExplorerTheme {
                val storagePermissionResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = {
                        val path = Environment.getExternalStorageDirectory().absolutePath
                        viewModel.onEvent(FileListEvent.OpenFile(path, this))
                        viewModel.onEvent(FileListEvent.Order(viewModel.state.value.fileOrder))
                        viewModel.onEvent(FileListEvent.GetFileHashInfoList)
                        viewModel.onEvent(FileListEvent.UploadFileHashInfoList(path))
                        viewModel.onEvent(FileListEvent.CompareFiles)
                    }
                )

                SideEffect {
                    storagePermissionResultLauncher.launch(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    FilesListScreen(
                        state = viewModel.state.value,
                        onOpenFile = { path ->
                            viewModel.onEvent(FileListEvent.OpenFile(path, this))
                            viewModel.onEvent(FileListEvent.Order(viewModel.state.value.fileOrder))
                            viewModel.onEvent(FileListEvent.CompareFiles)
                        },
                        onShare = { path ->
                            viewModel.onEvent(FileListEvent.ShareFile(path, this))
                        },
                        onGetBack = { parentPath ->
                            viewModel.onEvent(FileListEvent.OpenFile(parentPath, this))
                            viewModel.onEvent(FileListEvent.Order(viewModel.state.value.fileOrder))
                            viewModel.onEvent(FileListEvent.CompareFiles)
                        },
                        onToggleOrderSection = {
                            viewModel.onEvent(FileListEvent.ToggleOrderSection)
                        },
                        onOrderChange = {
                            viewModel.onEvent(FileListEvent.Order(it))
                        }
                    )
                }
            }
        }
    }
}