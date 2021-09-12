package com.really.goodnews.service.consumer

import com.really.goodnews.model.Photo
import com.really.goodnews.service.api.IAlbumApi
import io.reactivex.schedulers.Schedulers

class AlbumApiManager {

    companion object{
        val instance by lazy { AlbumApiManager() }
        private val albumApi by lazy { ServiceBuilder.buildService(IAlbumApi::class.java) }
    }

    fun getPhotos(albumId: Int, onFinished:(response: ArrayList<Photo>?) -> Unit){
        albumApi.getPhotosByAlbumId(albumId)
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