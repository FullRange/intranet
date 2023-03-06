package com.akra.intranet.data.remote.dto

import com.akra.intranet.domain.model.Log
import java.util.Date

data class LogDto(
    val id: Int?,
    val title: String,
    val description: String,
    val from: String,
    val to: String,
    val date: Date
)

fun LogDto.toLog(): Log {
    return Log(
        id = id,
        title = title,
        description = description,
        from = from,
        to = to,
        date = date
    )
}