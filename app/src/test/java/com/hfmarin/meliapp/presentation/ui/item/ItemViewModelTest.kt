package com.hfmarin.meliapp.presentation.ui.item

import androidx.lifecycle.SavedStateHandle
import com.hfmarin.meliapp.MainCoroutineRule
import com.hfmarin.meliapp.domain.model.Item
import com.hfmarin.meliapp.repository.MeliRepository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class ItemViewModelTest {
    private lateinit var subject: ItemViewModel

    private val mockRepository = mockk<MeliRepository>()
    private val savedStateHandle = mockk<SavedStateHandle>()

    @get:Rule
    val coroutineRule = MainCoroutineRule()


    @Before
    fun setUp() {
        every { savedStateHandle.get<String>(ItemViewModel.STATE_KEY_ITEM) } answers { "" }
        every { savedStateHandle.set<String>(ItemViewModel.STATE_KEY_ITEM, any()) } just Runs
        subject = ItemViewModel(mockRepository, savedStateHandle)
    }

    @Test
    fun `Given repository returns items then verify items are not empty`() {
        runTest {
            `Given repository return item`();`when getItemEvent arrives`();delay(2000);`then verify item id not empty`()
        }
    }

    @Test
    fun `Given repository returns exception then verify loading and items are not empty`() {
        runTest {
            `Given repository return exception`();`when getItemEvent arrives`();delay(2000); `then verify error not empty`()
        }
    }


    private fun `Given repository return item`() {
        coEvery { mockRepository.item(any()) } answers {
            val mockItem = Item(emptyList(), "id", 1.0, "", "", "", false, "")
            val mockResult = Result.success(mockItem)
            mockResult
        }
    }

    private fun `Given repository return exception`() {
        coEvery { mockRepository.item(any()) } answers {
            val mockResult = Result.failure<Item>(Throwable("error"))
            mockResult
        }
    }

    private fun `when getItemEvent arrives`() {
        subject.onTriggerEvent(ItemEvent.GetItemEvent("id"))
    }

    private fun `then verify item id not empty`() {
        assert(subject.item.value?.id!!.isNotEmpty())
    }

    private fun `then verify error not empty`() {
        assert(subject.error.value.isNotEmpty())
    }
}