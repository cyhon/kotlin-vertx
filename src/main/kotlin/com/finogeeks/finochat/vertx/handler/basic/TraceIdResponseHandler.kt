package com.finogeeks.finochat.vertx.handler.basic

import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext
import java.util.*

/**
 * Created by hetiu 2018/1/9.<br/>
 */

const val X_TRACE_ID: String = "x-trace-id"

class TraceIdResponseHandler : Handler<RoutingContext> {

    override fun handle(ctx: RoutingContext) {
        ctx.addHeadersEndHandler {
            val traceId: String = ctx.request().getHeader(X_TRACE_ID) ?: UUID.randomUUID().toString()
            ctx.response().putHeader(X_TRACE_ID, traceId)
        }

        ctx.next()
    }
}
