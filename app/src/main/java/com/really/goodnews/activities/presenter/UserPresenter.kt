package com.really.goodnews.activities.presenter

import com.really.goodnews.activities.contract.IUserContract
import com.really.goodnews.service.consumer.AlbumApiManager
import com.really.goodnews.service.consumer.UserApiManager

class UserPresenter(private val view: IUserContract.View): IUserContract.Presenter {

    private val userApi by lazy{ UserApiManager.instance }
    private val albumApi by lazy { AlbumApiManager.instance }

    override fun getPostsCount(userId: Int) {
        userApi.getUserPostsCount(userId){response ->
            view.setPostCount(response.toString())
        }
    }

    override fun getUserById(id: Int) {
        userApi.getUserById(id){response ->
            if (response != null) view.showUser(response)
            else view.showError()
        }
    }

    override fun getAlbumByUser(userId: Int) {
        userApi.getUserAlbums(userId){response ->
            if (response.isNullOrEmpty()) view.showError()
            else view.showAlbums(response, response.size)
        }
    }

    override fun getPhotoByAlbum(albumid: Int, position: Int) {
        albumApi.getPhotos(albumid){response ->
            if (!response.isNullOrEmpty()) view.setPhoto(response, position)
        }
    }
}