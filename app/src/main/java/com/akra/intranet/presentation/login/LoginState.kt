package com.akra.intranet.presentation.login

data class LoginState(
    val isLoading: Boolean = false,
    val wrongCredentials: Boolean = false,
    val error: String = ""
)