package com.really.goodnews.service.api

import com.really.goodnews.model.Photo
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface IAlbumApi {

    @GET("albums/{id}/photos")
    @Headers("content-type: application/json")
    fun getPhotosByAlbumId(@Path("id") id: Int): Observable<Response<ArrayList<Photo>>?>
}