package com.finogeeks.finochat.vertx.common

/**
 * Created by hetiu 2018/1/11.<br/>
 */

data class TraceId(val id: String) {
    override fun toString(): String = id
}