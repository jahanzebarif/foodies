package com.practice.task.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.practice.task.R
import com.practice.task.models.Restaurants

class ResturantsAdapter(private val restaurants: List<Restaurants>,private val context: Activity) : RecyclerView.Adapter<ResturantsAdapter.ResturantsAdapterViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResturantsAdapterViewHolder {
        return ResturantsAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_resturants,parent,false))
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    override fun onBindViewHolder(holder: ResturantsAdapterViewHolder, position: Int) {
        GlideToVectorYou
            .init()
            .with(context)
            .load(restaurants[position].cover_image.toUri(), holder.image)
        holder.name.text = restaurants[position].name
        holder.location.text = restaurants[position].address
        holder.rating.text = restaurants[position].rating
        holder.count.text = "(${restaurants[position].rating_count})"
    }

    class ResturantsAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image : ImageView = itemView.findViewById(R.id.imageView_resturants)
        val name : TextView = itemView.findViewById(R.id.textView_resturantName)
        val location : TextView = itemView.findViewById(R.id.textView_resturantLocation)
        val rating : TextView = itemView.findViewById(R.id.textView_resturantRating)
        val count : TextView = itemView.findViewById(R.id.textView_resturantCount)
    }
}