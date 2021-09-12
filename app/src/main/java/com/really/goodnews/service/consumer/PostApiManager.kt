package com.really.goodnews.service.consumer

import com.really.goodnews.model.Comment
import com.really.goodnews.model.Post
import com.really.goodnews.service.api.IPostApi
import io.reactivex.schedulers.Schedulers

class PostApiManager {

    companion object {
        val instance by lazy { PostApiManager() }
        private val postApi by lazy { ServiceBuilder.buildService(IPostApi::class.java) }
    }

    fun getAllPost(onFinished: (response: ArrayList<Post>?) -> Unit){
        postApi.getAllPosts()
            .subscribeOn(Schedulers.io())
            .subscribe({
                when(it.isSuccessful){
                    true -> onFinished(it.body())
                    else -> onFinished(null)
                }
            },{
                onFinished(null)
            })
    }

    fun getPostById(id: Int, onFinished: (response: Post?) -> Unit){
        postApi.getPostById(id)
            .subscribeOn(Schedulers.io())
            .subscribe({
                when(it?.isSuccessful){
                    true -> onFinished(it.body())
                    else -> onFinished(null)
                }
            },{
                onFinished(null)
            })
    }

    fun getPostComments(id: Int, onFinished: (response: ArrayList<Comment>?) -> Unit){
        postApi.getPostComments(id)
            .subscribeOn(Schedulers.io())
            .subscribe({
                when(it?.isSuccessful){
                    true -> onFinished(it.body())
                    else -> onFinished(null)
                }
            },{
                onFinished(null)
            })
    }
}