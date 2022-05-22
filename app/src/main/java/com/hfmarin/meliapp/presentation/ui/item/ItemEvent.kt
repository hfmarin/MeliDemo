package com.hfmarin.meliapp.presentation.ui.item

sealed class ItemEvent {
    data class GetItemEvent(
        val id: String
    ) : ItemEvent()
}