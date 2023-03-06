package com.akra.intranet.domain.model

import com.akra.intranet.data.remote.dto.LogDto
import java.util.*

class Log(
    val id: Int?,
    val title: String,
    val description: String,
    val from: String,
    val to: String,
    val date: Date
)

fun Log.toLogDto(): LogDto {
    return LogDto(
        id = id,
        title = title,
        description = description,
        from = from,
        to = to,
        date = date
    )
}