package com.example.foodie.timer

import app.cash.turbine.test
import com.example.foodie.utils.AppDispatchers
import com.example.foodie.utils.MainDispatcherRule
import com.example.foodie.utils.TestUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppTimerTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun dispatchers() = AppDispatchers(
        main = Dispatchers.also { it.setMain(UnconfinedTestDispatcher()) }.Main,
        io = StandardTestDispatcher(),
        default = StandardTestDispatcher(),
    )

    @Test
    fun `when start called then emit first event immediately`() = runTest {
        // given
        val tested = AppTimerImpl(dispatchers())

        // when
        tested.start()

        // then
        tested.tic.test {
            val firstTime = awaitItem()
            firstTime shouldBeEqualTo 0L
            currentTime shouldBeEqualTo 0L
            tested.finish()
        }
    }

    @Test
    fun `when start called then emit events every second`() = runTest {
        // given
        val tested = AppTimerImpl(dispatchers())

        // when
        tested.start()

        // then
        tested.tic.test {
            awaitItem() shouldBeEqualTo 0L
            currentTime shouldBeEqualTo 0L

            awaitItem() shouldBeEqualTo TestUtils.locationUpdateIntervalInMillis
            currentTime shouldBeEqualTo TestUtils.locationUpdateIntervalInMillis

            awaitItem() shouldBeEqualTo 2 * TestUtils.locationUpdateIntervalInMillis
            currentTime shouldBeEqualTo 2 * TestUtils.locationUpdateIntervalInMillis
            tested.finish()
        }
    }
}