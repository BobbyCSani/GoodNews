package com.really.goodnews.service.consumer

import com.really.goodnews.model.Album
import com.really.goodnews.model.Post
import com.really.goodnews.model.UserRemote
import com.really.goodnews.service.api.IUserApi
import io.reactivex.schedulers.Schedulers

class UserApiManager {

    companion object {
        val instance by lazy { UserApiManager() }
        private val userApi by lazy { ServiceBuilder.buildService(IUserApi::class.java) }
    }

    fun getUserPosts(userId: Int, onFinished: (response: ArrayList<Post>?) -> Unit){
        userApi.getUserPosts(userId)
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

    fun getUserPostsCount(userId: Int, onFinished: (response: Int?) -> Unit){
        userApi.getUserPosts(userId)
            .subscribeOn(Schedulers.io())
            .subscribe({
                when(it.isSuccessful){
                    true -> onFinished(it.body()?.size)
                    else -> onFinished(0)
                }
            },{
                onFinished(0)
            })
    }

    fun getAllUser(onFinished: (response: ArrayList<UserRemote>?) -> Unit){
        userApi.getAllUsers()
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

    fun getUserById(id: Int, onFinished: (response: UserRemote?) -> Unit){
        userApi.getUserById(id)
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

    fun getUserAlbums(userId: Int, onFinished: (response: ArrayList<Album>?) -> Unit){
        userApi.getAlbums(userId)
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
}