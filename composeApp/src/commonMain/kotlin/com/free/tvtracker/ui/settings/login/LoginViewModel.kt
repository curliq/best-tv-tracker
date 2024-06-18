package com.free.tvtracker.ui.settings.login

import com.free.tvtracker.data.session.SessionRepository
import com.free.tvtracker.expect.ui.ViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val sessionRepository: SessionRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {
    val result = MutableStateFlow(Result.Idle)

    enum class Result { Loading, Error, Success, Idle }

    data class LoginAction(val username: String, val password: String)

    fun login(action: LoginAction) {
        result.value = Result.Loading
        viewModelScope.launch(ioDispatcher) {
            val response = sessionRepository.login(action.username, action.password)
            response.coAsSuccess {
                result.emit(Result.Success)
            }
            response.coAsError {
                result.emit(Result.Error)
            }
        }
    }
}
