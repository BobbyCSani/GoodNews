package com.really.goodnews.service.api

import com.really.goodnews.model.Comment
import com.really.goodnews.model.Post
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface IPostApi {

    @GET("posts")
    @Headers("content-type: application/json")
    fun getAllPosts(): Observable<Response<ArrayList<Post>?>>

    @GET("posts/{id}")
    @Headers("content-type: application/json")
    fun getPostById(@Path("id") id: Int): Observable<Response<Post>?>

    @GET("posts/{id}/comments")
    @Headers("content-type: application/json")
    fun getPostComments(@Path("id") id: Int): Observable<Response<ArrayList<Comment>>?>

}