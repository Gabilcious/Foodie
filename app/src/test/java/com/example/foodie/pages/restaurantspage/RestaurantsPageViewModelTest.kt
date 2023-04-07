package com.example.foodie.pages.restaurantspage

import com.example.foodie.location.CurrentLocationProvider
import com.example.foodie.model.RestaurantId
import com.example.foodie.repository.favorite.FavoritesRepository
import com.example.foodie.repository.pages.restaurants.RestaurantsPageRepository
import com.example.foodie.utils.MainDispatcherRule
import com.example.foodie.utils.TestUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class RestaurantsPageViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val restaurantsPageRepository = mock<RestaurantsPageRepository>()
    private val currentLocationProvider = mock<CurrentLocationProvider> {
        on { currentLocation } doReturn flowOf(TestUtils.location)
    }

    private val favoritesRepositoryMock = object : FavoritesRepository {
        val map = HashMap<String, Boolean>()

        override fun setFavorite(id: RestaurantId, value: Boolean) {
            map[id.value] = value
        }

        override fun isFavorite(id: RestaurantId): Boolean = map.getOrDefault(id.value, false)

        fun clear() {
            map.clear()
        }
    }

    private fun tested() = RestaurantsPageViewModel(
        TestUtils.appConfig,
        restaurantsPageRepository,
        favoritesRepositoryMock,
        currentLocationProvider,
    )

    @After
    fun cleanUp() {
        favoritesRepositoryMock.clear()
    }

    @Test
    fun `when created then fetch sections`() = runTest {
        // when
        tested()

        // then
        verify(restaurantsPageRepository).getSections(TestUtils.location)
    }

    @Test
    fun `when onRetryClick then fetch sections again with last location`() = runTest {
        // when
        val tested = tested()
        tested.onRetryClick()

        // then
        verify(restaurantsPageRepository, times(2)).getSections(TestUtils.location)
    }

    @Test
    fun `when sections fetched contains vertical section then save it it state`() = runTest {
        // given
        whenever(restaurantsPageRepository.getSections(TestUtils.location))
            .doReturn(listOf(TestUtils.verticalSection))

        // when
        val tested = tested()

        // then
        tested.state.value.successData shouldBeEqualTo TestUtils.verticalSection
    }

    @Test
    fun `when sections fetched contains vertical section with too many restaurants then take only first`() =
        runTest {
            // given
            val tooManyRestaurants =
                TestUtils.generateRestaurants(2 * TestUtils.numberOfRestaurantsToDisplay)
            whenever(restaurantsPageRepository.getSections(TestUtils.location))
                .doReturn(
                    listOf(
                        TestUtils.verticalSection.copy(
                            restaurants = tooManyRestaurants,
                        ),
                    ),
                )

            // when
            val tested = tested()

            // then
            val expected = TestUtils.verticalSection.copy(
                restaurants = tooManyRestaurants.take(TestUtils.numberOfRestaurantsToDisplay),
            )
            tested.state.value.successData shouldBeEqualTo expected
        }

    @Test
    fun `when sections fetched does not contains vertical section then save null state`() =
        runTest {
            // given
            whenever(restaurantsPageRepository.getSections(TestUtils.location))
                .doReturn(listOf(mock()))

            // when
            val tested = tested()

            // then
            tested.state.value.successData shouldBeEqualTo null
        }

    @Test
    fun `when onFavoriteClick then set correct value`() = runTest {
        // given
        whenever(restaurantsPageRepository.getSections(TestUtils.location))
            .doReturn(listOf(TestUtils.verticalSection))

        // when
        val tested = tested()
        tested.onFavoriteClick(TestUtils.restaurant, true)

        // then
        val actual = tested.state.value.successData?.restaurants
            ?.first { it.id == TestUtils.restaurant.id }
            ?.isFavorite
        actual shouldBe true
    }

    @Test
    fun `when display restaurants make then set correct isFavorite values`() = runTest {
        // given
        whenever(restaurantsPageRepository.getSections(TestUtils.location))
            .doReturn(listOf(TestUtils.verticalSection))
        val testId1 = TestUtils.verticalSection.restaurants[0].id
        val testId2 = TestUtils.verticalSection.restaurants[2].id

        // when
        favoritesRepositoryMock.setFavorite(testId1, true)
        favoritesRepositoryMock.setFavorite(testId2, true)
        val tested = tested()

        // then
        tested.state.value.successData?.restaurants?.forEach {
            it.isFavorite shouldBe (it.id == testId1 || it.id == testId2)
        }
    }
}