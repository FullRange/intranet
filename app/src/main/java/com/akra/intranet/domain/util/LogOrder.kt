package com.akra.intranet.domain.util

sealed class LogOrder(val orderType: OrderType) {
    class Id(orderType: OrderType): LogOrder(orderType)
    class Title(orderType: OrderType): LogOrder(orderType)
    class Date(orderType: OrderType): LogOrder(orderType)
}