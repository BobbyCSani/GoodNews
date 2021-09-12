package com.really.goodnews.activities.contract

import com.really.goodnews.model.Album
import com.really.goodnews.model.Photo
import com.really.goodnews.model.UserRemote

interface IUserContract {

    interface Presenter {
        fun getPostsCount(userId: Int)
        fun getUserById(id: Int)
        fun getAlbumByUser(userId: Int)
        fun getPhotoByAlbum(albumid: Int, position: Int)
    }

    interface View {
        fun showUser(user: UserRemote)
        fun showAlbums(list: ArrayList<Album>, qty: Int)
        fun setPostCount(count: String)
        fun setPhoto(list: ArrayList<Photo>, position: Int)
        fun showError()
    }
}