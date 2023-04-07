package com.example.foodie.repository.favorite

import android.content.Context
import android.content.SharedPreferences
import com.example.foodie.model.RestaurantId
import org.amshove.kluent.shouldBe
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class FavoritesRepositoryTest {

    private val id = RestaurantId("test-id")
    private val editor = mock<SharedPreferences.Editor> {
        on { putBoolean(any(), any()) } doReturn it
    }
    private val sharedPreferences = mock<SharedPreferences> {
        on { edit() } doReturn editor
    }
    private val context = mock<Context> {
        on { getSharedPreferences(any(), any()) } doReturn sharedPreferences
    }

    private val tested = FavoritesRepositoryImpl(context)

    @Test
    fun `when isFavorite called then return false by default`() {
        // when // then
        tested.isFavorite(id) shouldBe false
    }

    @Test
    fun `when isFavorite called then return value from sharedPreferences`() {
        // given
        whenever(sharedPreferences.getBoolean(eq(id.value), any())).thenReturn(true)

        // when // then
        tested.isFavorite(id) shouldBe true
    }

    @Test
    fun `when setFavorite called then save proper data in sharedPreferences`() {
        // given
        val idCaptor = argumentCaptor<String>()
        val valueCaptor = argumentCaptor<Boolean>()
        whenever(sharedPreferences.getBoolean(eq(id.value), any())).thenReturn(true)

        // when
        tested.setFavorite(id, true)

        // then
        verify(editor).putBoolean(idCaptor.capture(), valueCaptor.capture())
        verify(editor).apply()
        idCaptor.firstValue shouldBe id.value
        valueCaptor.firstValue shouldBe true
    }
}