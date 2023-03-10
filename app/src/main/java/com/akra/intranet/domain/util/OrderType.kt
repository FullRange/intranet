package com.akra.intranet.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}