package com.akra.intranet.domain.use_case.get_logs

import com.akra.intranet.common.handleApiRequest
import com.akra.intranet.data.remote.dto.toLog
import com.akra.intranet.domain.repository.LogsRepository
import com.akra.intranet.domain.util.LogOrder
import com.akra.intranet.domain.util.OrderType
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetLogsUseCase @Inject constructor(
    private val repository: LogsRepository
) {
    operator fun invoke(
        logOrder: LogOrder = LogOrder.Date(OrderType.Descending)
    ) = handleApiRequest {
        delay(1000L)
        repository.getLogs().map { it.toLog() }.run {
            when (logOrder.orderType) {
                OrderType.Ascending -> {
                    when (logOrder) {
                        is LogOrder.Id -> sortedBy { it.id }
                        is LogOrder.Title -> sortedBy { it.title }
                        is LogOrder.Date -> sortedBy { it.date }
                    }
                }
                OrderType.Descending -> {
                    when (logOrder) {
                        is LogOrder.Id -> sortedByDescending { it.id }
                        is LogOrder.Title -> sortedByDescending { it.title }
                        is LogOrder.Date -> sortedByDescending { it.date }
                    }
                }
            }
        }
    }
}