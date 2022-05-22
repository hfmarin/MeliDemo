package com.hfmarin.meliapp.presentation.ui.item_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfmarin.meliapp.domain.model.Item
import com.hfmarin.meliapp.presentation.ui.item_list.ItemListEvent.NewSearchEvent
import com.hfmarin.meliapp.repository.MeliRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemListViewModel
@Inject
constructor(
    private val repository: MeliRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val items: MutableState<List<Item>> = mutableStateOf(listOf())
    val error = mutableStateOf("")
    val query = mutableStateOf("")
    val loading = mutableStateOf(false)
    var itemListScrollPosition = 0


    init {
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let {
            setQuery(it)
        }
        onTriggerEvent(NewSearchEvent)
    }

    fun onTriggerEvent(event: ItemListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is NewSearchEvent -> {
                        newSearch()
                    }
                }
            } catch (e: Exception) {
                Log.e(ItemListViewModel::class.simpleName, "error on event trigger" + e.message)
            }
        }
    }

    private suspend fun newSearch() {
        error.value = ""
        loading.value = true
        resetSearchState()
        delay(2000)
        val result = repository.search(query = query.value)
        if (result.isSuccess) {
            result.getOrNull()?.let { items.value = it }
        } else {
            error.value = result.exceptionOrNull()?.message ?: "error getting list"
        }
        loading.value = false
    }

    fun onQueryChanged(query: String) {
        setQuery(query)
    }

    private fun resetSearchState() {
        items.value = listOf()
        onChangeItemScrollPosition(0)
    }

    fun onChangeItemScrollPosition(position: Int) {
        itemListScrollPosition = position
    }

    private fun setQuery(query: String) {
        this.query.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }

    companion object {
        const val STATE_KEY_QUERY = "item.state.query.key"
    }
}


