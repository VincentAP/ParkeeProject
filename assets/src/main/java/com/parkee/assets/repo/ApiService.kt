package com.parkee.assets.repo

import com.parkee.assets.model.User
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("v1/profiles")
    fun login(
        @Header("Authorization") authHeader: String
    ): Single<List<User>>

//    @FormUrlEncoded
//    @POST("token")
//    fun login(
//        @Header("Authorization") authHeader: String,
//        @Field("grant_type") credentials: String
//    ): Call<ResponseBody>
}