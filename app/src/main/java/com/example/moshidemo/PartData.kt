package com.example.moshidemo

import com.squareup.moshi.JsonClass

/**
 * @author wang
 * date:  2020/10/20
 * Tip : 严格要求自己，对每一行代码负责
 * description：(用一句话描述)
 */
@JsonClass(generateAdapter = true)
data class PartData ( var id: Long, var itemName: String)