package com.akra.intranet.presentation.addedit

import com.akra.intranet.domain.model.Log

data class AddEditState(
    val item: Log? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)