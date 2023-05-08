package com.strangenaut.boosterfileexplorer.feature_filereader.presentation.fileslist

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.List
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.strangenaut.boosterfileexplorer.R
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.util.FileOrder
import com.strangenaut.boosterfileexplorer.feature_filereader.presentation.fileslist.components.FileListItem
import com.strangenaut.boosterfileexplorer.feature_filereader.presentation.fileslist.components.OrderSection
import java.io.File

@Composable
fun FilesListScreen(
    state: FileListState,
    onOpenFile: (path: String?) -> Unit,
    onShare: (path: String?) -> Unit,
    onGetBack: (parentPath: String?) -> Unit,
    onToggleOrderSection: () -> Unit,
    onOrderChange: (order: FileOrder) -> Unit
) {
    val changedFilesCount = state.nestedFiles.filter {
        it.hasChangedSinceLastLaunch
    }.size

    Scaffold(
        topBar = {
            val parentFile = state.currentFile.parentPath?.let { File(it) }
            val isParentFileAccessible = parentFile?.listFiles() != null

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    enabled = isParentFileAccessible,
                    onClick = {
                        onGetBack(parentFile?.absolutePath)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        tint = if (isParentFileAccessible)
                            MaterialTheme.colors.primary
                        else
                            MaterialTheme.colors.primaryVariant
                    )
                }
                Text(
                    text = if (state.currentFile.name != "0")
                        state.currentFile.name
                    else
                        stringResource(R.string.storage),
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.primary
                )
                IconButton(
                    onClick = {
                        onToggleOrderSection()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Outlined.List,
                        contentDescription = stringResource(R.string.sort)
                    )
                }
            }
        },
        bottomBar = {
            if (changedFilesCount > 0) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.files_changed, changedFilesCount),
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    ) {
        Column {
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    fileOrder = state.fileOrder,
                    onOrderChange = {
                        onOrderChange(it)
                    }
                )
            }

            if(state.nestedFiles.isEmpty()) {
                Text(
                    text = stringResource(R.string.empty),
                    textAlign = TextAlign.Center,
                    softWrap = true,
                    fontSize = MaterialTheme.typography.h2.fontSize,
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
                return@Column
            }

            LazyColumn {
                items(state.nestedFiles.size) { number ->
                    val fileItem = state.nestedFiles[number]

                    FileListItem(
                        fileItem = fileItem,
                        onClick = {
                            onOpenFile(fileItem.path)
                        },
                        onShare = {
                            onShare(fileItem.path)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Spacer(modifier = Modifier.height(it.calculateBottomPadding()))
        }
    }
}