package com.akra.intranet.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akra.intranet.R
import com.akra.intranet.presentation.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    var loginTextFieldState by remember {
        mutableStateOf("")
    }
    var passwordTextFieldState by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = true) {
        viewModel.effect.collectLatest { effect ->
            when(effect) {
                LoginEffect.LoggedIn -> {
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(0)
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = loginTextFieldState,
                label = {
                    Text(stringResource(R.string.login_btn_txt))
                },
                onValueChange = {
                    loginTextFieldState = it
                },
                singleLine = true
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = passwordTextFieldState,
                label = {
                    Text(stringResource(R.string.password_txt))
                },
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = {
                    passwordTextFieldState = it
                },
                singleLine = true
            )

            if (state.wrongCredentials) {
                Text(
                    text = stringResource(R.string.wrong_credentials_error),
                    style = TextStyle(color = Color.Red)
                )
            }

            if (state.error.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.error_msg, state.error),
                    style = TextStyle(color = Color.Red)
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(16.dp),
                onClick = {
                    viewModel.login(loginTextFieldState, passwordTextFieldState)
                }) {
                Text(text = stringResource(R.string.login_text))
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}