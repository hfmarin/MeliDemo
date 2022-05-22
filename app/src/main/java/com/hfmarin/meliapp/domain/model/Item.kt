package com.hfmarin.meliapp.domain.model

data class Item(
    val attributes: List<Attribute>,
    val id: String,
    val price: Double,
    val thumbnail: String,
    val thumbnail_id: String,
    val title: String,
    val use_thumbnail_id: Boolean,
    val currency_id: String,
)