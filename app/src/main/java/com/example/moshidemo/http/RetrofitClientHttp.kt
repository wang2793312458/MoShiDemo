package com.example.moshidemo.http

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * @author wang
 * @date {2020/5/11}
 * Tip : 严格要求自己，对每一行代码负责
 * description：
 */
class RetrofitClientHttp private constructor(baseUrl: String) {
    private val TAG = "RetrofitClientHttp"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            )
        )
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
//        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

    companion object {
        fun getInstance(baseUrl: String): RetrofitClientHttp {
            return RetrofitClientHttp(baseUrl)
        }
    }

    // 2
    private val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    // 3
    private val loggingInterceptor: HttpLoggingInterceptor
        get() {
            val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.i(TAG, message)
                }
            })
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            return interceptor
        }
}