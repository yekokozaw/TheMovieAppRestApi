package com.padc.themovieapp.data.vos

import com.google.gson.annotations.SerializedName

data class ProductionCountryVO(
    @SerializedName("iso_3166_1")
    val isoKey : String?,

    @SerializedName("name")
    val name : String?
)
