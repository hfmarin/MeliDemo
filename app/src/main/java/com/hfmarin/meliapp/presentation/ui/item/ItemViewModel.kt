package com.hfmarin.meliapp.presentation.ui.item

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfmarin.meliapp.domain.model.Item
import com.hfmarin.meliapp.repository.MeliRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject
constructor(
    private val meliRepository: MeliRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    val error = mutableStateOf("")
    val item: MutableState<Item?> = mutableStateOf(null)
    val loading = mutableStateOf(false)

    init {
        state.get<String>(STATE_KEY_ITEM)?.let {
            onTriggerEvent(ItemEvent.GetItemEvent(it))
        }
    }

    fun onTriggerEvent(itemEvent: ItemEvent) {
        viewModelScope.launch {
            when (itemEvent) {
                is ItemEvent.GetItemEvent -> {
                    getItem(itemEvent.id)
                }
            }
        }
    }

    private suspend fun getItem(id: String) {
        error.value = ""
        loading.value = true
        val item = meliRepository.item(id = id)
        if (item.isSuccess) {
            this.item.value = item.getOrNull()
            state.set(STATE_KEY_ITEM, item.getOrNull()?.id)
        } else {
            error.value = item.exceptionOrNull()?.message ?: "error getting item"
        }
        loading.value = false
    }

    companion object {
        const val STATE_KEY_ITEM = "state.key.itemId"
    }

}

