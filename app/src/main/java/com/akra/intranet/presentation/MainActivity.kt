package com.akra.intranet.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akra.intranet.common.Constants
import com.akra.intranet.presentation.addedit.AddEditScreen
import com.akra.intranet.presentation.home.HomeScreen
import com.akra.intranet.presentation.login.LoginScreen
import com.akra.intranet.presentation.ui.theme.AkraIntranetTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AkraIntranetTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.LoginScreen.route
                    ) {
                        composable(
                            route = Screen.LoginScreen.route
                        ) {
                            LoginScreen(navController = navController)
                        }
                        composable(
                            route = Screen.HomeScreen.route
                        ) {
                            HomeScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditScreen.route + "/{${Constants.PARAM_LOG_ID}}"
                        ) {
                            AddEditScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
