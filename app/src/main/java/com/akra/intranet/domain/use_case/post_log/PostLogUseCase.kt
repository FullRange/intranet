package com.akra.intranet.domain.use_case.post_log

import com.akra.intranet.common.handleApiRequest
import com.akra.intranet.data.remote.dto.LogDto
import com.akra.intranet.domain.repository.LogsRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class PostLogUseCase @Inject constructor(
    private val repository: LogsRepository
) {
    operator fun invoke(log: LogDto) = handleApiRequest {
        delay(1000L)
        repository.postLog(log)
    }
}