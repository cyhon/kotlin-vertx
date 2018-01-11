package com.finogeeks.finochat.vertx.dto

import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import java.net.InetAddress

/**
 * Created by hetiu 2017/8/22.<br/>
 */

val serviceName: String = InetAddress.getLocalHost().hostName

enum class ErrorCode(val message: String) {
    FC_NOT_FOUND("请求的API不存在"),
    FC_ERROR("服务异常");

    override fun toString(): String {
        return JsonObject(mapOf(
            "errcode" to this.name,
            "error" to this.message,
            "service" to serviceName
        )).toString()
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
        return when (reply) {
            is String -> reply
            is JsonObject -> reply.toString()
            is ErrorCode -> reply.toString()
            else -> Json.encodePrettily(reply)
        }
    }
}