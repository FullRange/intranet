package com.akra.intranet.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akra.intranet.common.Resource
import com.akra.intranet.domain.use_case.login.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    private val _effect = MutableSharedFlow<LoginEffect>()
    val effect = _effect.asSharedFlow()

    fun login(login: String, password: String) {
        loginUseCase(login, password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data == true) {
                        _state.value = LoginState()
                        _effect.emit(LoginEffect.LoggedIn)
                    } else {
                        _state.value = LoginState(wrongCredentials = true)
                    }
                }
                is Resource.Loading -> {
                    _state.value = LoginState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = LoginState(error = result.message.orEmpty())
                }
            }
        }.launchIn(viewModelScope)
    }
}