package com.akra.intranet.domain.repository

import com.akra.intranet.data.remote.dto.LogDto

interface LogsRepository {

    suspend fun login(login: String, password: String): Boolean

    suspend fun getLogs(): List<LogDto>

    suspend fun getLog(logId: Int): LogDto?

    suspend fun postLog(log: LogDto): Boolean
}