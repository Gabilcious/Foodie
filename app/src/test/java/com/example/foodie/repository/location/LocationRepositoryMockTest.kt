package com.example.foodie.repository.location

import com.example.foodie.model.Location
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import junitparams.naming.TestCaseName
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
class LocationRepositoryMockTest {

    private val tested = LocationRepositoryMockImpl()

    @Test
    fun `when getCurrentLocation called 11th time return first location again`() {
        // given
        val firstLocation = tested.getCurrentLocation()
        repeat(9) { tested.getCurrentLocation() }

        // when
        val lastLocation = tested.getCurrentLocation()

        // then
        lastLocation shouldBeEqualTo firstLocation
    }

    @Test
    @Parameters(method = "locations")
    @TestCaseName("when getCurrentLocation called {0}th time then return {1}")
    fun `when getCurrentLocation called then returns correct location`(
        times: Int,
        location: Location,
    ) {
        // given
        repeat(times) { tested.getCurrentLocation() }

        // when // then
        tested.getCurrentLocation() shouldBeEqualTo location
    }

    @Suppress("Unused", "UnusedPrivateMember")
    private fun locations() = listOf(
        listOf(0, Location("60.169418", "24.931618")),
        listOf(1, Location("60.169818", "24.932906")),
        listOf(2, Location("60.170005", "24.935105")),
        listOf(3, Location("60.169108", "24.936210")),
        listOf(4, Location("60.168355", "24.934869")),
        listOf(5, Location("60.170187", "24.930599")),
        listOf(6, Location("60.167560", "24.932562")),
        listOf(7, Location("60.168254", "24.931532")),
        listOf(8, Location("60.169012", "24.930341")),
        listOf(9, Location("60.170085", "24.929569")),
    )
}