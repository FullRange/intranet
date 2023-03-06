package com.akra.intranet.presentation.addedit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.akra.intranet.common.formatToString
import com.akra.intranet.common.formatToTimeString
import com.akra.intranet.common.parseToDate
import com.akra.intranet.common.parseToTime
import com.akra.intranet.domain.model.Log
import com.akra.intranet.presentation.Screen
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
        viewModel.effect.collect { effect ->
            when(effect) {
                is AddEditEffect.ItemInitialized -> {
                    effect.item?.apply {
                        titleTextFieldState = title
                        descriptionTextFieldState = description
                        dateTextFieldState = date.formatToString()
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
                text = "Log item"
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.DarkGray)
            )

            if (state.error.isNotEmpty()) {
                Text(
                    text = "Error: ${state.error}",
                    style = TextStyle(color = Color.Red)
                )
            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = titleTextFieldState,
                label = {
                    Text("Title")
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
                    Text("Description")
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
                        val cal = Calendar.getInstance()

                        dateTextFieldState.parseToDate()?.let { date ->
                            cal.time = date
                        }

                        DatePickerDialog(context,
                            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                                dateTextFieldState = Calendar.getInstance().apply {
                                    set(year, month, dayOfMonth, 0, 0)
                                }.time.formatToString()
                            },
                            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    },
                value = dateTextFieldState,
                label = {
                    Text("Date")
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
                        val cal = Calendar.getInstance()

                        fromTextFieldState.parseToTime()?.let {
                            cal.time = it
                        }

                        TimePickerDialog(context,
                            {_, hour : Int, minute: Int ->
                                fromTextFieldState = Calendar.getInstance().apply {
                                    set(Calendar.HOUR_OF_DAY, hour)
                                    set(Calendar.MINUTE, minute)
                                }.time.formatToTimeString()
                            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true
                        ).show()
                    },
                value = fromTextFieldState,
                label = {
                    Text("From")
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
                        val cal = Calendar.getInstance()

                        toTextFieldState.parseToTime()?.let {
                            cal.time = it
                        }

                        TimePickerDialog(context,
                            {_, hour : Int, minute: Int ->
                                toTextFieldState = Calendar.getInstance().apply {
                                    set(Calendar.HOUR_OF_DAY, hour)
                                    set(Calendar.MINUTE, minute)
                                }.time.formatToTimeString()
                            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true
                        ).show()
                    },
                value = toTextFieldState,
                label = {
                    Text("To")
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
                Text(text = "Save")
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}