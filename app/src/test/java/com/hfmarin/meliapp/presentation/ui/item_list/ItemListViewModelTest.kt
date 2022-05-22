package com.hfmarin.meliapp.presentation.ui.item_list

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
internal class ItemListViewModelTest {
    private lateinit var subject: ItemListViewModel

    private val mockRepository = mockk<MeliRepository>()
    private val savedStateHandle = mockk<SavedStateHandle>()

    @get:Rule
    val coroutineRule = MainCoroutineRule()


    @Before
    fun setUp() {
        every { savedStateHandle.get<String>(ItemListViewModel.STATE_KEY_QUERY) } answers { "" }
        every { savedStateHandle.set<String>(ItemListViewModel.STATE_KEY_QUERY, any()) } just Runs
        subject = ItemListViewModel(mockRepository, savedStateHandle)
    }

    @Test
    fun `Given repository returns items then verify items are not empty`() {
        runTest {
            `Given repository return items`();`when newSearchEvent arrives`();delay(2000);`then verify items not empty`()
        }
    }

    @Test
    fun `Given repository returns exception then verify loading and items are not empty`() {
        runTest {
            `Given repository return exception`();`when newSearchEvent arrives`();delay(2000); `then verify error not empty`()
        }
    }


    private fun `Given repository return items`() {
        coEvery { mockRepository.search(any()) } answers {
            val mockItem = mockk<Item>()
            val mockResult = Result.success(listOf(mockItem, mockItem))
            mockResult
        }
    }

    private fun `Given repository return exception`() {
        coEvery { mockRepository.search(any()) } answers {
            val mockResult = Result.failure<List<Item>>(Throwable("error"))
            mockResult
        }
    }

    private fun `when newSearchEvent arrives`() {
        subject.query.value = "value"
        subject.onTriggerEvent(ItemListEvent.NewSearchEvent)
    }

    private fun `then verify items not empty`() {
        assert(subject.items.value.isNotEmpty())
    }


    private fun `then verify error not empty`() {
        assert(subject.error.value.isNotEmpty())
    }
}