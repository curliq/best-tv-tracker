package com.free.tvtracker.services

import com.free.tvtracker.data.user.UserRepository
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

class FcmService : FirebaseMessagingService() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        val repo: UserRepository = get()
        scope.launch {
            repo.postFcmToken(token)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
