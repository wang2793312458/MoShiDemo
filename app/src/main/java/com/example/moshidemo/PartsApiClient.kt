package com.example.moshidemo

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

/**
 * @author wang
 * date:  2020/10/20
 * Tip : 严格要求自己，对每一行代码负责
 * description：(用一句话描述)
 */
interface PartsApiClient {
    @GET("parts")
    fun getPartsAsync(): Deferred<Response<List<PartData>>>

    @POST("parts")
    fun addPartAsync(@Body newPart: PartData): Deferred<Response<Void>>

    @DELETE("parts/{id}")
    fun deletePartAsync(@Path("id") id: Long): Deferred<Response<Void>>

    @PUT("parts/{id}")
    fun updatePartAsync(@Path("id") id: Long, @Body newPart: PartData): Deferred<Response<Void>>
}