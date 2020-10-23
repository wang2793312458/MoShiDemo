package com.example.moshidemo.http

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author wang
 * date:  2020/10/20
 * Tip : 严格要求自己，对每一行代码负责
 * description：(用一句话描述)
 */
@JsonClass(generateAdapter = true)
data class HttpCodeMsgData(
    @Json(name = "code") val code: Int,
    @Json(name = "msg") val msg: String,
)