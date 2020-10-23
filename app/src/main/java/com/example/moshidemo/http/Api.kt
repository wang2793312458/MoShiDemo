package com.example.moshidemo.http

import retrofit2.http.GET

/**
 * @author wang
 * date:  2020/10/23
 * Tip : 严格要求自己，对每一行代码负责
 * description：(用一句话描述)
 */
interface Api {
    companion object {
        const val API_DEFAULT_HOST = "http://47.108.26.86:27017/"
        const val API_LOAD_IMAGE = "http://47.108.26.86:27017/"
        const val API_UPLOAD_IMAGE = ""
    }

    @GET("uaa/oauth/token")
    suspend fun getPartsAsync(): PartData
}