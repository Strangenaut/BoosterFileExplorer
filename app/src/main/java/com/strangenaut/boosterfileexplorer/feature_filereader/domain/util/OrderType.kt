package com.strangenaut.boosterfileexplorer.feature_filereader.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}