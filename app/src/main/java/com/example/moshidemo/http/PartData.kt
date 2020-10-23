package com.example.moshidemo.http

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author wang
 * date:  2020/10/23
 * Tip : 严格要求自己，对每一行代码负责
 * description：(用一句话描述)
 */
//data class PartData(
//    val id: String
//)
@JsonClass(generateAdapter = true)
data class PartData(
    @Json(name = "id")
    val id: String
)