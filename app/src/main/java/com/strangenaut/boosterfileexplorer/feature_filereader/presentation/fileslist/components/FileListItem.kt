package com.strangenaut.boosterfileexplorer.feature_filereader.presentation.fileslist.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.model.FileItem
import com.strangenaut.boosterfileexplorer.R

@Composable
fun FileListItem(
    fileItem: FileItem,
    linePadding: Float = 32.dp.value,
    lineWidth: Float = 2.dp.value,
    onClick: () -> Unit = { },
    onShare: () -> Unit = { },
    modifier: Modifier = Modifier,
) {
    val fileDescription = if (fileItem.sizeFormatted.isEmpty())
        fileItem.creationDateTime.toString()
    else
        "${fileItem.sizeFormatted}  |  ${fileItem.creationDateTime}"

    Surface(
        color = if (fileItem.hasChangedSinceLastLaunch)
            MaterialTheme.colors.secondary
        else
            MaterialTheme.colors.background,
        modifier = modifier.clickable { onClick() }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Image(
                    painter = painterResource(id = fileItem.iconId ?: R.drawable.file),
                    contentDescription = fileItem.extension,
                    modifier = Modifier.padding(16.dp)
                )
                Column {
                    Text(
                        text = fileItem.name,
                        style = MaterialTheme.typography.h2,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Text(
                        text = fileDescription,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
            if (!fileItem.isDirectory) {
                IconButton(
                    onClick = {
                        onShare()
                    },
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = stringResource(R.string.share),
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }

    var width = 0
    val lineColor = MaterialTheme.colors.primaryVariant
    Canvas(
        modifier = modifier.onPlaced { layoutCoordinates ->
            width = layoutCoordinates.size.width
        },
        onDraw = {
            drawLine(
                color = lineColor,
                strokeWidth = lineWidth,
                start = Offset(x = linePadding, y = 0f),
                end = Offset(x = width.toFloat() - linePadding, y = 0f)
            )
        }
    )
}