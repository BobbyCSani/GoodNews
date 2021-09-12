package com.really.goodnews.activities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.really.goodnews.databinding.AdapterCommentBinding
import com.really.goodnews.model.Comment
import com.really.goodnews.service.local.db.UserDatabase

class CommentAdapter(private val list: ArrayList<Comment>): RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    class ViewHolder(view: AdapterCommentBinding): RecyclerView.ViewHolder(view.root){
        private val username = view.commentUsername
        private val commentText = view.commentText

        fun bind(data: Comment){
            username.text = data.email
            commentText.text = data.body
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = AdapterCommentBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = list.size
}