package com.strangenaut.boosterfileexplorer.feature_filereader.domain.usecase

import com.strangenaut.boosterfileexplorer.feature_filereader.domain.model.FileItem
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.util.FileOrder
import com.strangenaut.boosterfileexplorer.feature_filereader.domain.util.OrderType
import java.util.*

class OrderFiles {

    operator fun invoke(order: FileOrder, filesList: List<FileItem>): List<FileItem> {
        return when(order.orderType) {
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
    }
}