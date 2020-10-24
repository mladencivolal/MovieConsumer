package com.example.movieconsumer.presentation.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieconsumer.R
import com.example.movieconsumer.data.model.actor.Actor
import com.example.movieconsumer.databinding.ActorListItemBinding
import com.example.movieconsumer.presentation.movies.MoviesAdapter

class ActorsAdapter(recyclerView: RecyclerView)  : RecyclerView.Adapter<ActorsAdapter.MyViewHolder>(){
    private val actorsList: MutableList<Actor> = mutableListOf()
    lateinit var onItemClickListener: MoviesAdapter.OnItemClickListener
    lateinit var binding: ActorListItemBinding

    inner class MyViewHolder(
        val binding: ActorListItemBinding,
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(actor: Actor) {
            binding.actorName.text = actor.name
            val imageURL = "https://image.tmdb.org/t/p/w45" + actor.profilePath
            Glide.with(binding.actorImage.context)
                .load(imageURL)
                .into(binding.actorImage)
        }

        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
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
        holder.bind(actorsList[position])    }

    override fun getItemCount(): Int = actorsList.size

    fun setList(actors: List<Actor>) {
        actorsList.clear()
        actorsList.addAll(actors)
        notifyDataSetChanged()
    }
}