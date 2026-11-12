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

class PopularResturantsAdapter(private val popularRestaurants: List<Restaurants>,private val context: Activity) :
    RecyclerView.Adapter<PopularResturantsAdapter.PopularResturantsAdapterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularResturantsAdapterViewHolder {
        return PopularResturantsAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_popular_resturants,parent,false))
    }

    override fun getItemCount(): Int {
        return popularRestaurants.size
    }

    override fun onBindViewHolder(holder: PopularResturantsAdapterViewHolder, position: Int) {
        GlideToVectorYou
            .init()
            .with(context)
            .load(popularRestaurants[position].cover_image.toUri(), holder.image)
        holder.name.text = popularRestaurants[position].name
        holder.distance.text = popularRestaurants[position].distance
        holder.time.text = popularRestaurants[position].delivery_time.replace("mins","")
    }

    class PopularResturantsAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image : ImageView = itemView.findViewById(R.id.imageView_popularResturants)
        val name : TextView = itemView.findViewById(R.id.textView_popularResturantName)
        val distance : TextView = itemView.findViewById(R.id.textView_popularResturants_distance)
        val time : TextView = itemView.findViewById(R.id.textView_popularResturants_time)
    }
}