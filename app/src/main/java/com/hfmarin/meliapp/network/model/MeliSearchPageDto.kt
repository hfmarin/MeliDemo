package com.hfmarin.meliapp.network.model

import com.google.gson.annotations.SerializedName

data class MeliSearchPageDto(
    @SerializedName("country_default_time_zone")
    var country_default_time_zone: String,
    @SerializedName("query")
    var query: String,
    @SerializedName("results")
    var results: List<ResultDto>,
    @SerializedName("site_id")
    var site_id: String,
)