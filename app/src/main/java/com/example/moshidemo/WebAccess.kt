package com.example.moshidemo

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * @author wang
 * date:  2020/10/20
 * Tip : 严格要求自己，对每一行代码负责
 * description：(用一句话描述)
 */
object WebAccess {

    val partsApi: PartsApiClient by lazy {
        Log.d("WebAccess", "Creating retrofit client")
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(OkHttpClientFactory.getInstance(null, true))
            .build()
        return@lazy retrofit.create(PartsApiClient::class.java)
    }
}