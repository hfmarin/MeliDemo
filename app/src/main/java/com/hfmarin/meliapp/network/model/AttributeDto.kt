package com.hfmarin.meliapp.network.model

import com.google.gson.annotations.SerializedName

data class AttributeDto(
    @SerializedName("name")
    var name: String,
    @SerializedName("value_name")
    var value : String?
)
