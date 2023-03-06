package com.akra.intranet.presentation.addedit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akra.intranet.common.Constants
import com.akra.intranet.common.Resource
import com.akra.intranet.domain.model.Log
import com.akra.intranet.domain.model.toLogDto
import com.akra.intranet.domain.use_case.get_log.GetLogUseCase
import com.akra.intranet.domain.use_case.post_log.PostLogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val getLogUseCase: GetLogUseCase,
    private val postLogUseCase: PostLogUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(AddEditState())
    val state: State<AddEditState> = _state

    private val _effect = MutableSharedFlow<AddEditEffect>()
    val effect = _effect.asSharedFlow()

    init {
        savedStateHandle.get<String>(Constants.PARAM_LOG_ID)?.let { logId ->
            val id = logId.toInt()
            if (id >= 0) {
                getLogById(id)
            }
        }
    }

    private fun getLogById(logId: Int) {
        getLogUseCase(logId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = AddEditState(item = result.data)
                    _effect.emit(AddEditEffect.ItemInitialized(result.data))
                }
                is Resource.Loading -> {
                    _state.value = AddEditState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = AddEditState(error = result.message.orEmpty())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun postLog(log: Log) {
        postLogUseCase(log.toLogDto()).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data == true) {
                        _effect.emit(AddEditEffect.ItemSaved)
                    } else {
                        _state.value = AddEditState(error = "Cannot add/edit log")
                    }
                }
                is Resource.Loading -> {
                    _state.value = AddEditState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = AddEditState(error = result.message.orEmpty())
                }
            }
        }.launchIn(viewModelScope)
    }
}