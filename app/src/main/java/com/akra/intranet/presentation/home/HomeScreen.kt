package com.akra.intranet.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akra.intranet.R
import com.akra.intranet.presentation.Screen
import com.akra.intranet.presentation.home.components.LogListItem

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = stringResource(R.string.log_item_list_title)
                )

                Button(
                    modifier = Modifier
                        .padding(16.dp),
                    onClick = { navController.navigate(Screen.AddEditScreen.route + "/-1") }) {
                    Text(text = stringResource(R.string.add_log_btn_txt))
                }
            }

            if (state.error.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.error_msg, state.error),
                    style = TextStyle(color = Color.Red)
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.DarkGray)
            )

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(state.logList.size) {
                    LogListItem(
                        item = state.logList[it],
                        onItemClick = { log ->
                            navController.navigate(Screen.AddEditScreen.route + "/${log.id}")
                        }
                    )
                }
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}