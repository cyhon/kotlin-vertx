package com.finogeeks.finochat.vertx.core

import com.finogeeks.finochat.vertx.handler.basic.TRACE_ID_CTX_KEY
import com.finogeeks.finochat.vertx.handler.basic.TRACE_ID_HEADER_KEY
import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.client.HttpRequest
import org.slf4j.MDC

fun RoutingContext.getTraceId(): String {
    return this.get(TRACE_ID_CTX_KEY)
}

fun <E> traceable(handler: Handler<E>): Handler<E> {
    val traceId = MDC.get(TRACE_ID_CTX_KEY)
    return Handler {
        MDC.put(TRACE_ID_CTX_KEY, traceId)
        handler.handle(it)
    }
}

inline fun <E> traceable(crossinline transform: (E) -> Unit): Handler<E> {
    val traceId = MDC.get(TRACE_ID_CTX_KEY)
    return Handler {
        MDC.put(TRACE_ID_CTX_KEY, traceId)
        transform(it)
    }
}

fun <T> HttpRequest<T>.withTrace(): HttpRequest<T> {
    return this.putHeader(TRACE_ID_HEADER_KEY, MDC.get(TRACE_ID_CTX_KEY))
}