package com.strangenaut.boosterfileexplorer.feature_filereader.domain.util

sealed class FileOrder(val orderType: OrderType) {
    class TitleAndExtension(orderType: OrderType): FileOrder(orderType)
    class Title(orderType: OrderType): FileOrder(orderType)
    class Size(orderType: OrderType): FileOrder(orderType)
    class Date(orderType: OrderType): FileOrder(orderType)
    class Extension(orderType: OrderType): FileOrder(orderType)

    fun copy(orderType: OrderType): FileOrder {
        return when(this) {
            is TitleAndExtension -> TitleAndExtension(orderType)
            is Title -> Title(orderType)
            is Size -> Size(orderType)
            is Date -> Date(orderType)
            is Extension -> Extension(orderType)
        }
    }
}
