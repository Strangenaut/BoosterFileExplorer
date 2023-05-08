package com.strangenaut.boosterfileexplorer.feature_filereader.presentation.fileslist.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.util.FileOrder
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.util.OrderType
import com.strangenaut.boosterfileexplorer.R

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    fileOrder: FileOrder = FileOrder.TitleAndExtension(OrderType.Descending),
    onOrderChange: (FileOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(R.string.title_and_extension),
                selected = fileOrder is FileOrder.TitleAndExtension,
                onSelect = { onOrderChange(FileOrder.TitleAndExtension(fileOrder.orderType)) }
            )
            DefaultRadioButton(
                text = stringResource(R.string.title),
                selected = fileOrder is FileOrder.Title,
                onSelect = { onOrderChange(FileOrder.Title(fileOrder.orderType)) }
            )
            DefaultRadioButton(
                text = stringResource(R.string.size),
                selected = fileOrder is FileOrder.Size,
                onSelect = { onOrderChange(FileOrder.Size(fileOrder.orderType)) }
            )
            DefaultRadioButton(
                text = stringResource(R.string.date),
                selected = fileOrder is FileOrder.Date,
                onSelect = { onOrderChange(FileOrder.Date(fileOrder.orderType)) }
            )
            DefaultRadioButton(
                text = stringResource(R.string.extension),
                selected = fileOrder is FileOrder.Extension,
                onSelect = { onOrderChange(FileOrder.Extension(fileOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = stringResource(R.string.ascending),
                selected = fileOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(fileOrder.copy(OrderType.Ascending))
                }
            )
            DefaultRadioButton(
                text = stringResource(R.string.descending),
                selected = fileOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(fileOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}