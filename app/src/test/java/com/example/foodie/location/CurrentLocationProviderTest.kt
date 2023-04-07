package com.example.foodie.location

import app.cash.turbine.test
import com.example.foodie.repository.location.LocationRepository
import com.example.foodie.timer.AppTimer
import com.example.foodie.utils.AppDispatchers
import com.example.foodie.utils.MainDispatcherRule
import com.example.foodie.utils.TestUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.amshove.kluent.shouldBe
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class CurrentLocationProviderTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun dispatchers() = AppDispatchers(
        main = Dispatchers.also { it.setMain(UnconfinedTestDispatcher()) }.Main,
        io = StandardTestDispatcher(),
        default = StandardTestDispatcher(),
    )

    private val appTimer = mock<AppTimer>()
    private val locationRepository = mock<LocationRepository> {
        on { getCurrentLocation() } doReturn TestUtils.location
    }

    private fun tested() = CurrentLocationProviderImpl(
        TestUtils.appConfig,
        locationRepository,
        appTimer,
        dispatchers(),
    )

    @Test
    fun `when time changes then update location`() = runTest {
        // given
        whenever(appTimer.tic).doReturn(flowOf(0L))

        // when
        val tested = tested()

        // then
        tested.currentLocation.test {
            val item = awaitItem()
            item shouldBe TestUtils.location
        }
        verify(locationRepository).getCurrentLocation()
    }

    @Test
    fun `when time changes but interval not passed then not update location`() = runTest {
        // given
        whenever(appTimer.tic).doReturn(flowOf(0L, 1L, 2L))

        // when
        val tested = tested()

        // then
        tested.currentLocation.test {
            val item = awaitItem()
            item shouldBe TestUtils.location
        }
        verify(locationRepository).getCurrentLocation()
    }

    @Test
    fun `when time changes and interval passed then update location again`() = runTest {
        // given
        whenever(appTimer.tic).doReturn(
            flowOf(
                0L,
                TestUtils.appConfig.locationUpdateIntervalInSeconds * 1_000L,
            ),
        )

        // when
        val tested = tested()

        // then
        tested.currentLocation.test {
            awaitItem()
            val item = awaitItem()
            item shouldBe TestUtils.location
        }
        verify(locationRepository, times(2)).getCurrentLocation()
    }
}