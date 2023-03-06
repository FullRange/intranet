package com.akra.intranet.domain.use_case.login

import com.akra.intranet.common.handleApiRequest
import com.akra.intranet.domain.repository.LogsRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LogsRepository
) {
    operator fun invoke(login: String, password: String) = handleApiRequest {
        delay(2000L)
        repository.login(login, password)
    }
}