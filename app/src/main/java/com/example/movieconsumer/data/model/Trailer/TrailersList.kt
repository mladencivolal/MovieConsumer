package com.example.movieconsumer.data.model.Trailer


import com.google.gson.annotations.SerializedName

data class TrailersList(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val trailers: List<Trailer>
)