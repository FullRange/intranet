package com.akra.intranet.presentation.addedit

import com.akra.intranet.domain.model.Log

sealed class AddEditEffect {
    object ItemSaved : AddEditEffect()
    data class ItemInitialized(val item: Log?) : AddEditEffect()
}