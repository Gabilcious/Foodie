package com.example.foodie.repository.pages.restaurants

import com.example.foodie.repository.model.ApiImage
import com.example.foodie.repository.model.ApiSectionItem
import com.example.foodie.repository.model.ApiTemplate
import com.example.foodie.repository.model.ApiVenue
import com.example.foodie.utils.TestUtils
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class RestaurantsPageMapperTest {

    @Test
    fun `given template is OTHER when mapToSectionOrNull called then return null`() {
        // when
        val actual =
            TestUtils.verticalApiSection.copy(template = ApiTemplate.OTHER).mapToSectionOrNull()

        // then
        actual shouldBe null
    }

    @Test
    fun `given template is null when mapToSectionOrNull called then return null`() {
        // when
        val actual = TestUtils.verticalApiSection.copy(template = null).mapToSectionOrNull()

        // then
        actual shouldBe null
    }

    @Test
    fun `given template is VENUE_VERTICAL_LIST when mapToSectionOrNull called then return correct section`() {
        // when
        val actual = TestUtils.verticalApiSection.mapToSectionOrNull()

        // then
        actual shouldBeEqualTo TestUtils.verticalSection
    }

    @Test
    fun `given name is null when mapToSectionOrNull called then return null`() {
        // when
        val actual = TestUtils.verticalApiSection.copy(name = null).mapToSectionOrNull()

        // then
        actual shouldBe null
    }

    @Test
    fun `given title is null when mapToSectionOrNull called then return null`() {
        // when
        val actual = TestUtils.verticalApiSection.copy(title = null).mapToSectionOrNull()

        // then
        actual shouldBe null
    }

    @Test
    fun `given template is VENUE_VERTICAL_LIST when mapToSectionOrNull called then return correct section without incorrect restaurants`() {
        // given
        val sectionItemWithoutName = ApiSectionItem(
            ApiVenue(null, "test-name", "test-description"),
            ApiImage("test-url"),
        )
        val sectionItemWithoutTitle = ApiSectionItem(
            ApiVenue("test-id", null, "test-description"),
            ApiImage("test-url"),
        )
        val items = TestUtils.verticalApiSection.items + listOf(
            sectionItemWithoutName,
            sectionItemWithoutTitle,
        )

        // when
        val actual = TestUtils.verticalApiSection.copy(
            template = ApiTemplate.VENUE_VERTICAL_LIST,
            items = items,
        ).mapToSectionOrNull()

        // then
        actual shouldBeEqualTo TestUtils.verticalSection
    }
}