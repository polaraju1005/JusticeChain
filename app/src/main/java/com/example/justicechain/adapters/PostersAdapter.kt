package com.example.justicechain.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.justicechain.R
import com.example.justicechain.models.PostersData

class PostersAdapter(
    private val context: Context,
    private val postersDataList: ArrayList<PostersData>
) : RecyclerView.Adapter<PostersAdapter.HomeViewHolder>() {

    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view.findViewById<ImageView>(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_poster_display, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        Glide.with(context).load(postersDataList[position].bannerImageUrl.toString())
            .placeholder(R.drawable.slideshow_corner).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return postersDataList.size
    }
}