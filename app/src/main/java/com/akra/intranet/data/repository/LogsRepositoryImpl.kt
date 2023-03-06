package com.akra.intranet.data.repository

import com.akra.intranet.data.remote.IntranetApi
import com.akra.intranet.data.remote.dto.LogDto
import com.akra.intranet.domain.repository.LogsRepository
import java.util.*
import javax.inject.Inject

class LogsRepositoryImpl @Inject constructor(
    private val api: IntranetApi
) : LogsRepository {

    override suspend fun login(login: String, password: String): Boolean {
        return (login == "admin" && password == "password")
    }

    override suspend fun getLogs(): List<LogDto> {
        return fakeLogsList.toList()
    }

    override suspend fun getLog(logId: Int): LogDto? {
        return fakeLogsList.find { it.id == logId }
    }

    override suspend fun postLog(log: LogDto): Boolean {
        log.id?.let { id ->
            fakeLogsList.indexOfFirst { it.id == id }.also { index ->
                fakeLogsList[index] = log
            }
        } ?: fakeLogsList.add(log.copy(id = fakeLogsList.size))
        return true
    }

    companion object {
        val fakeLogsList = MutableList(2) {
            LogDto(
                id = it,
                title = "Title $it",
                description = "Description $it",
                from = "8:00",
                to = "16:00",
                date = Calendar.getInstance().apply {
                    add(Calendar.DAY_OF_MONTH, -1 * it)
                }.time
            )
        }
    }
}