package com.example.tmdbclient.data.model.artist


import com.google.gson.annotations.SerializedName

data class ActorList(
    @SerializedName("results")
    val actors: List<Actor>
)