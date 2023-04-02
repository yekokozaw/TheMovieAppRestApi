package com.padc.themovieapp.data.vos

import com.google.gson.annotations.SerializedName

data class CollectionVO(

    @SerializedName("id")
    val collectionId: Int?,

    @SerializedName("name")
    val name : String?,

    @SerializedName("poster_path")
    val posterPath : String?,

    @SerializedName("backdrop_path")
    val backdropPath : String?
)
