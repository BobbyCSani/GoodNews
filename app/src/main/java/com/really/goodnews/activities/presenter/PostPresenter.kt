package com.really.goodnews.activities.presenter

import com.really.goodnews.activities.contract.IPostContract
import com.really.goodnews.model.User
import com.really.goodnews.service.consumer.PostApiManager
import com.really.goodnews.service.consumer.UserApiManager
import com.really.goodnews.service.local.db.UserDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PostPresenter(private val view: IPostContract.View): IPostContract.Presenter {

    private val postApi by lazy { PostApiManager.instance }
    private val userApi by lazy { UserApiManager.instance }

    override fun getAllPost() {
        postApi.getAllPost { response ->
            if (response.isNullOrEmpty()) view.showEmpty()
            else view.showData(response)
        }
    }

    override fun getPostById(id: Int) {
        postApi.getPostById(id){ post ->
            if (post == null) view.showEmpty()
            else view.showPost(post)
        }
    }

    override fun getPostByUser(userId: Int) {
        userApi.getUserPosts(userId) { response ->
            if (response.isNullOrEmpty()) view.showEmpty()
            else view.showData(response)
        }
    }

    override fun getCommentByPostId(id: Int) {
        postApi.getPostComments(id){ comments ->
            if (comments.isNullOrEmpty()) view.showEmpty()
            else view.showComments(comments)
        }
    }

    override suspend fun getUsername(userDb: UserDatabase, id: Int, email: String?, position: Int) {
        GlobalScope.launch {
            val user = if (email.isNullOrEmpty()) userDb.userDao().getUserById(id)
            else userDb.userDao().getUserByEmail(email)
            view.setUsername(user, position)
        }.join()
    }
}