package com.akra.intranet.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akra.intranet.common.Resource
import com.akra.intranet.domain.use_case.get_logs.GetLogsUseCase
import com.akra.intranet.domain.util.LogOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLogsUseCase: GetLogsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        getLogs(state.value.logOrder)
    }

    private fun getLogs(logOrder: LogOrder) {
        getLogsUseCase(logOrder).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = HomeState(logList = result.data.orEmpty())
                }
                is Resource.Loading -> {
                    _state.value = HomeState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = HomeState(error = result.message.orEmpty())
                }
            }
        }.launchIn(viewModelScope)
    }
}