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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akra.intranet.presentation.Screen

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
        viewModel.effect.collect { effect ->
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
                    Text("Login")
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
                    Text("Password")
                },
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = {
                    passwordTextFieldState = it
                },
                singleLine = true
            )

            if (state.wrongCredentials) {
                Text(
                    text = "Wrong credentials",
                    style = TextStyle(color = Color.Red)
                )
            }

            if (state.error.isNotEmpty()) {
                Text(
                    text = "Error: ${state.error}",
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
                Text(text = "Login")
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}