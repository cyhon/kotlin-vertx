package com.finogeeks.finochat.vertx.core

import com.finogeeks.finochat.vertx.handler.basic.TRACE_ID_CTX_KEY
import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext

fun RoutingContext.getTraceId(): String {
    return this.get(TRACE_ID_CTX_KEY)
}

fun <E> mixinTraceId(handler: Handler<E>): Handler<E> {
    val traceId = TraceIdRepo.getCurrentTraceId()
    return Handler {
        TraceIdRepo.traceTL.set(traceId)
        handler.handle(it)
    }
}

inline fun <E> mixinTraceId(crossinline transform: (E) -> Unit): Handler<E> {
    val traceId = TraceIdRepo.getCurrentTraceId()
    return Handler {
        TraceIdRepo.traceTL.set(traceId)
        transform(it)
    }
}