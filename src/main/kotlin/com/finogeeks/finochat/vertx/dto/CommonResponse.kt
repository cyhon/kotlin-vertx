package com.finogeeks.finochat.vertx.dto

import io.vertx.core.json.JsonObject

/**
 * Created by hetiu 2017/8/22.<br/>
 */

enum class ErrorCode(val message: String) {
    FC_ERROR("服务异常");

    override fun toString(): String {
        return JsonObject(mapOf("errcode" to this.name, "error" to this.message)).toString()
    }
}

data class CommonResponse(val status: Int = 200, private val reply: Any = JsonObject()) {

    var header: HashMap<String, String>? = null

    fun addHeader(key: String, value: String): CommonResponse {
        if (header == null) {
            header = hashMapOf(key to value)
        } else {
            header?.put(key, value)
        }
        return this
    }

    override fun toString(): String {
        return reply.toString()
    }
}