package com.finogeeks.finochat.vertx.handler.basic

import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext
import java.util.*

/**
 * Created by hetiu 2018/1/9.<br/>
 */

const val TRACE_ID_HEADER_KEY: String = "x-trace-id"
const val TRACE_ID_CTX_KEY: String = "TRACE_ID"

class TraceIdHandler : Handler<RoutingContext> {

    override fun handle(ctx: RoutingContext) {
        val traceId = ctx.request().getHeader(TRACE_ID_HEADER_KEY) ?: UUID.randomUUID().toString()
        ctx.put(TRACE_ID_CTX_KEY, traceId)

        ctx.addHeadersEndHandler {
            ctx.response().putHeader(TRACE_ID_HEADER_KEY, traceId)
        }

        ctx.next()
    }
}
