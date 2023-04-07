package com.example.foodie.timer

import com.example.foodie.utils.AppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppTimerImpl @Inject constructor(
    dispatchers: AppDispatchers,
) : AppTimer {

    companion object {
        const val APP_TIMER_INTERVAL_IN_MILLIS = 1_000L
    }

    private val coroutineScope = CoroutineScope(SupervisorJob() + dispatchers.default)

    private var isRunning = false
    private var isStarted = false
    private var timeSinceStart = 0L

    override val tic: Flow<Long> = flow {
        while (true) {
            if (isRunning) {
                emit(timeSinceStart)
            }
            if (isStarted) {
                timeSinceStart += APP_TIMER_INTERVAL_IN_MILLIS
            }
            delay(APP_TIMER_INTERVAL_IN_MILLIS)
        }
    }.shareIn(
        scope = coroutineScope,
        started = SharingStarted.Eagerly,
    )

    override fun start() {
        isStarted = true
        isRunning = true
    }

    override fun pause() {
        isRunning = false
    }

    override fun finish() {
        isRunning = false
        isStarted = false
        coroutineScope.cancel()
    }
}