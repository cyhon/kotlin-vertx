package com.finogeeks.finochat.vertx.core

import com.finogeeks.finochat.vertx.handler.basic.TRACE_ID_CTX_KEY
import io.vertx.ext.web.RoutingContext

fun RoutingContext.getTraceId(): String {
    return this.get(TRACE_ID_CTX_KEY)
}