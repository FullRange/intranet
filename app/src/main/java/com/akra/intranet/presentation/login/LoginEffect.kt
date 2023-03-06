package com.akra.intranet.presentation.login

sealed class LoginEffect {
    object LoggedIn : LoginEffect()
}