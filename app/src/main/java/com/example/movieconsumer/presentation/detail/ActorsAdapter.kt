package com.example.movieconsumer.presentation.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieconsumer.R
import com.example.movieconsumer.data.model.actor.Actor
import com.example.movieconsumer.databinding.ActorListItemBinding

class ActorsAdapter(private val context: Context) : RecyclerView.Adapter<ActorsAdapter.MyViewHolder>() {
    private val actorsList: MutableList<Actor> = mutableListOf()
    lateinit var binding: ActorListItemBinding

    inner class MyViewHolder(
        val binding: ActorListItemBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(actor: Actor) {
            binding.tvActor.text = actor.name
            val character = if(actor.character.isEmpty()) "Unknown" else actor.character
            binding.tvCharacter.text = context.resources.getString(R.string.movie_activity_actor_character, character)
            val imageURL = "https://image.tmdb.org/t/p/w45" + actor.profilePath
            Glide.with(binding.ivActor.context)
                .load(imageURL)
                .into(binding.ivActor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.actor_list_item,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(actorsList[position])
    }

    override fun getItemCount(): Int = actorsList.size

    fun setList(actors: List<Actor>) {
        actorsList.clear()
        actorsList.addAll(actors)
        notifyDataSetChanged()
    }
}