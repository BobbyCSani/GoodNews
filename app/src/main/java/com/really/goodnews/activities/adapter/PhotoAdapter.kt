package com.really.goodnews.activities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.really.goodnews.databinding.AdapterPhotoBinding
import com.really.goodnews.model.Photo


class PhotoAdapter(private val list: ArrayList<Photo>, private val listener: Listener): RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    interface Listener {
        fun onClickPhoto(position: Int)
    }

    class ViewHolder(view: AdapterPhotoBinding): RecyclerView.ViewHolder(view.root){

        private val imageView = view.photo
        private val imageLoader = Glide.with(view.root)

        fun bind(photo: Photo, listener: Listener, position: Int){
            itemView.setOnClickListener { listener.onClickPhoto(position) }
            val url = GlideUrl(
                photo.thumbnailUrl, LazyHeaders.Builder()
                    .addHeader("User-Agent", WebSettings.getDefaultUserAgent(itemView.context))
                    .build()
            )
            imageLoader.load(url).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = AdapterPhotoBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = list[position]
        holder.bind(photo, listener, position)
    }

    override fun getItemCount(): Int = list.size
}