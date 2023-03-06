package com.akra.intranet.presentation.home

import com.akra.intranet.domain.model.Log

data class HomeState(
    val logList: List<Log> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)