package com.akra.intranet.presentation.addedit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akra.intranet.R
import com.akra.intranet.common.*
import com.akra.intranet.domain.model.Log
import com.akra.intranet.presentation.Screen
import kotlinx.coroutines.flow.collectLatest
import java.util.*

@Composable
fun AddEditScreen(
    navController: NavController,
    viewModel: AddEditViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val state = viewModel.state.value

    var titleTextFieldState by remember {
        mutableStateOf("")
    }
    var descriptionTextFieldState by remember {
        mutableStateOf("")
    }
    var dateTextFieldState by remember {
        mutableStateOf("")
    }
    var fromTextFieldState by remember {
        mutableStateOf("")
    }
    var toTextFieldState by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = true) {
        viewModel.effect.collectLatest { effect ->
            when(effect) {
                is AddEditEffect.ItemInitialized -> {
                    effect.item?.apply {
                        titleTextFieldState = title
                        descriptionTextFieldState = description
                        dateTextFieldState = date.formatToDateString()
                        fromTextFieldState = from
                        toTextFieldState = to
                    }
                }
                AddEditEffect.ItemSaved -> {
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(0)
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.log_item_title)
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.DarkGray)
            )

            if (state.error.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.error_msg, state.error),
                    style = TextStyle(color = Color.Red)
                )
            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = titleTextFieldState,
                label = {
                    Text(stringResource(R.string.title_text))
                },
                onValueChange = {
                    titleTextFieldState = it
                },
                singleLine = true
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = descriptionTextFieldState,
                label = {
                    Text(stringResource(R.string.description_text))
                },
                onValueChange = {
                    descriptionTextFieldState = it
                },
                singleLine = true
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        context.getDatePicker(initDate = dateTextFieldState.parseToDate()) {
                            dateTextFieldState = it.formatToDateString()
                        }.show()
                    },
                value = dateTextFieldState,
                label = {
                    Text(stringResource(R.string.date_text))
                },
                onValueChange = {
                    dateTextFieldState = it
                },
                singleLine = true,
                enabled = false
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        context.getTimePicker(initTime = fromTextFieldState.parseToTime()) {
                            fromTextFieldState = it.formatToTimeString()
                        }.show()
                    },
                value = fromTextFieldState,
                label = {
                    Text(stringResource(R.string.from_text))
                },
                onValueChange = {
                    fromTextFieldState = it
                },
                singleLine = true,
                enabled = false
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        context.getTimePicker(initTime = toTextFieldState.parseToTime()) {
                            toTextFieldState = it.formatToTimeString()
                        }.show()
                    },
                value = toTextFieldState,
                label = {
                    Text(stringResource(R.string.to_text))
                },
                onValueChange = {
                    toTextFieldState = it
                },
                singleLine = true,
                enabled = false
            )

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    viewModel.postLog(
                        Log(
                            id = state.item?.id,
                            title = titleTextFieldState,
                            description = descriptionTextFieldState,
                            from = fromTextFieldState,
                            to = toTextFieldState,
                            date = dateTextFieldState.parseToDate() ?: Date()
                        )
                    )
                }
            ) {
                Text(text = stringResource(R.string.save_txt))
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
