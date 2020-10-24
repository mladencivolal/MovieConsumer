package com.example.movieconsumer.data.model.actor


import com.google.gson.annotations.SerializedName

data class ActorsList(
    @SerializedName("cast")
    val actors: List<Actor>,
    @SerializedName("id")
    val id: Int
)