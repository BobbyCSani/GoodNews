package com.really.goodnews.activities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.really.goodnews.databinding.AdapterAlbumBinding
import com.really.goodnews.model.Album
import com.really.goodnews.model.Photo

class AlbumAdapter(private val list: ArrayList<Album>, private val listener: Listener): RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    interface Listener {
        fun getPhoto(albumId: Int, position: Int)
        fun onclickPhoto(albumPosition: Int, photoPosition: Int)
    }

    class ViewHolder(view: AdapterAlbumBinding, private val listener: Listener): RecyclerView.ViewHolder(view.root), PhotoAdapter.Listener{

        private val albumName = view.albumName
        private val recyclerView = view.albumRecyclerView
        private lateinit var adapter: PhotoAdapter
        private var albumPosition: Int = 0
        private val listPhoto = arrayListOf<Photo>()

        fun bind(album: Album, position: Int){
            albumPosition = position
            albumName.text = album.title
            adapter = PhotoAdapter(listPhoto, this)
            recyclerView.layoutManager = GridLayoutManager(itemView.context, 3)
            recyclerView.adapter = adapter
            if (album.photos.isNullOrEmpty()) {
                listener.getPhoto(album.id, position)
            } else {
                listPhoto.clear()
                listPhoto.addAll(album.photos ?: arrayListOf())
                adapter.notifyDataSetChanged()
            }
        }
        override fun onClickPhoto(position: Int) {
            listener.onclickPhoto(albumPosition, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = AdapterAlbumBinding.inflate(inflater, parent, false)
        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val album = list[position]
        holder.bind(album, position)
    }

    override fun getItemCount(): Int = list.size
}