package com.example.moshidemo.http

/**
 * @author wang
 * date:  2020/8/25
 * Tip : 严格要求自己，对每一行代码负责
 * description：(网络请求设置)
 */
object HttpFactory {
    val instance: Api by lazy {
        return@lazy RetrofitClientHttp
            .getInstance(Api.API_DEFAULT_HOST)
            .create(Api::class.java)
    }
}