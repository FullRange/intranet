package com.akra.intranet.presentation.home

import com.akra.intranet.domain.model.Log
import com.akra.intranet.domain.util.LogOrder
import com.akra.intranet.domain.util.OrderType

data class HomeState(
    val logList: List<Log> = emptyList(),
    val logOrder: LogOrder = LogOrder.Date(OrderType.Descending),
    val isLoading: Boolean = false,
    val error: String = ""
)