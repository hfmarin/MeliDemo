package com.hfmarin.meliapp.network.model

import com.google.gson.annotations.SerializedName

data class ResultDto(
    @SerializedName("attributes")
    var attributes : List<AttributeDto>,
    @SerializedName("id")
    var id: String,
    @SerializedName("price")
    var price: Double,
    @SerializedName("thumbnail")
    var thumbnail: String,
    @SerializedName("thumbnail_id")
    var thumbnail_id: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("use_thumbnail_id")
    var use_thumbnail_id: Boolean,
    @SerializedName("currency_id")
    var currency_id: String
)