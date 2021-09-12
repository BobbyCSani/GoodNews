package com.really.goodnews.service.api

import com.really.goodnews.model.*
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface IUserApi {

    @GET("users")
    @Headers("content-type: application/json")
    fun getAllUsers(): Observable<Response<ArrayList<UserRemote>?>>

    @GET("users/{id}")
    @Headers("content-type: application/json")
    fun getUserById(@Path("id") id: Int): Observable<Response<UserRemote?>>

    @GET("users/{id}/albums")
    @Headers("content-type: application/json")
    fun getAlbums(@Path("id") id: Int): Observable<Response<ArrayList<Album>?>>

    @GET("users/{id}/posts")
    @Headers("content-type: application/json")
    fun getUserPosts(@Path("id") id: Int): Observable<Response<ArrayList<Post>?>>
}