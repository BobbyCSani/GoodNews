package com.really.goodnews.activities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.really.goodnews.databinding.AdapterPostBinding
import com.really.goodnews.model.Post
import com.really.goodnews.service.local.db.UserDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostAdapter(private val list: ArrayList<Post>, private val listener: Listener, private val userDb: UserDatabase): RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    interface Listener {
        fun onClickPost(post: Post)
        fun onClickUser(userId: Int)
        fun sharePost(post: Post)
        fun getUser(id: Int, position: Int)
    }

    class ViewHolder(view: AdapterPostBinding): RecyclerView.ViewHolder(view.root){
        private val username = view.postUsername
        private val companyName = view.postUserCompanyName
        private val title = view.postTitle
        private val body = view.postBody
        private val comment = view.commentButton
        private val share = view.shareButton

        fun bind(data: Post, listener: Listener, position: Int){
            if (data.username.isNullOrEmpty()) {
                listener.getUser(data.userId, position)
            }
            username.text = data.username
            companyName.text = data.companyName
            title.text = data.title
            body.text = data.body
            username.setOnClickListener { listener.onClickUser(data.userId) }
            body.setOnClickListener { listener.onClickPost(data) }
            comment.setOnClickListener { listener.onClickPost(data) }
            share.setOnClickListener { listener.sharePost(data) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = AdapterPostBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.bind(data, listener, position)
    }

    override fun getItemCount(): Int = list.size
}