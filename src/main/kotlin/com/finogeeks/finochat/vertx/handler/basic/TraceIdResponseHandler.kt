package com.finogeeks.finochat.vertx.handler.basic

import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext
import org.apache.logging.log4j.ThreadContext
import java.util.*

/**
 * Created by hetiu 2018/1/9.<br/>
 */

const val X_TRACE_ID: String = "x-trace-id"

class TraceIdResponseHandler : Handler<RoutingContext> {

    override fun handle(ctx: RoutingContext) {
        val traceId: String = ctx.request().getHeader(X_TRACE_ID) ?: UUID.randomUUID().toString()
        ctx.put("traceId", traceId)

        ctx.addHeadersEndHandler {
            ctx.response().putHeader(X_TRACE_ID, traceId)
        }

        ThreadContext.put("traceId", traceId)

        ctx.next()
    }
}
