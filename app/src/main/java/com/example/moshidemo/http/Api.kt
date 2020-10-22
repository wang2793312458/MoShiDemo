package com.example.moshidemo.http

import retrofit2.Call
import retrofit2.http.POST

/**
 * @author wang
 * date:  2020/10/22
 * Tip : 严格要求自己，对每一行代码负责
 * description：(用一句话描述)
 */
interface Api {
    @POST("user/login")
    suspend fun login(): Call<User>
}