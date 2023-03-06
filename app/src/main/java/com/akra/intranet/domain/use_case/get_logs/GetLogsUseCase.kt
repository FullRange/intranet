package com.akra.intranet.domain.use_case.get_logs

import com.akra.intranet.common.handleApiRequest
import com.akra.intranet.data.remote.dto.toLog
import com.akra.intranet.domain.repository.LogsRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetLogsUseCase @Inject constructor(
    private val repository: LogsRepository
) {
    operator fun invoke() = handleApiRequest {
        delay(1000L)
        repository.getLogs().map { it.toLog() }
    }
}