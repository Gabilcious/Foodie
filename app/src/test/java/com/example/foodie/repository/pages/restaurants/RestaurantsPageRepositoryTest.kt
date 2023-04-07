package com.example.foodie.repository.pages.restaurants

import com.example.foodie.repository.RetrofitServiceFactory
import com.example.foodie.repository.model.ApiRestaurantsPage
import com.example.foodie.repository.pages.PagesService
import com.example.foodie.utils.TestUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class RestaurantsPageRepositoryTest {

    private val pagesService = mock<PagesService>()
    private val retrofitServiceFactory = mock<RetrofitServiceFactory<PagesService>> {
        on { create(eq(TestUtils.pagesBaseUrl), any()) } doReturn pagesService
    }

    private val tested = RestaurantsPageRepositoryImpl(
        TestUtils.appConfig,
        retrofitServiceFactory,
    )

    @Test
    fun `given sections in response when getSections called then return sections`() =
        runTest {
            // given
            val apiRestaurantsPage = ApiRestaurantsPage(
                sections = listOf(
                    TestUtils.verticalApiSection,
                    TestUtils.verticalApiSection.copy(name = "test-name-2"),
                ),
            )
            whenever(
                pagesService.getRestaurantsPage(
                    TestUtils.location.latitude,
                    TestUtils.location.longitude,
                ),
            )
                .thenReturn(apiRestaurantsPage)

            // when
            val actual = tested.getSections(TestUtils.location)

            // then
            actual shouldBeEqualTo listOf(
                TestUtils.verticalSection,
                TestUtils.verticalSection.copy(name = "test-name-2"),
            )
        }
}