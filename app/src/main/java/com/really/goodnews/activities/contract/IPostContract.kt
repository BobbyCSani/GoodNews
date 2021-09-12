package com.really.goodnews.activities.contract

import com.really.goodnews.model.Comment
import com.really.goodnews.model.Post
import com.really.goodnews.model.User
import com.really.goodnews.service.local.db.UserDatabase

interface IPostContract {

    interface Presenter {
        fun getAllPost()
        fun getPostById(id: Int)
        fun getPostByUser(userId: Int)
        fun getCommentByPostId(id: Int)
        suspend fun getUsername(userDb: UserDatabase, id: Int, email: String?, position: Int)
    }

    interface View {
        fun showData(list: ArrayList<Post>){}
        fun showComments(list: ArrayList<Comment>){}
        fun showPost(post: Post){}
        fun showEmpty(){}
        fun setUsername(user: User, position: Int){}
    }
}