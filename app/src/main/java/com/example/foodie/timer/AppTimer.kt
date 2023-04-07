package com.example.foodie.timer

import kotlinx.coroutines.flow.Flow

/**
 * AppTimer emits events in the hot flow ([AppTimer.tic]) every second from the moment [AppTimer.start]
 * is called until [AppTimer.finish] or [AppTimer.pause] is called. Each tic tells how many milliseconds
 * has passed since the timer was first started.
 */
interface AppTimer {
    /**
     * Hot flow to listen. Each tic in the flow contains information how many milliseconds passed
     * since the timer was first started.
     */
    val tic: Flow<Long>

    /**
     * Starts or resumes the flow. When flow is already running it does nothing.
     */
    fun start()

    /**
     * Pauses the flow.
     */
    fun pause()

    /**
     * Stops the flow. It cancels coroutine where flow is shared and cleans up.
     */
    fun finish()
}