package com.finogeeks.finochat.vertx.core


object TraceIdRepo {

    val traceTL = object : ThreadLocal<String>() {
        override fun initialValue(): String {
            return "" // TODO: how to design...
        }
    }

    fun getCurrentTraceId(): String {
        return traceTL.get()
    }

}