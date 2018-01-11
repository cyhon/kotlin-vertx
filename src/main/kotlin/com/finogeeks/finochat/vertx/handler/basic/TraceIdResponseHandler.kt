package com.finogeeks.finochat.vertx.handler.basic

import com.finogeeks.finochat.vertx.common.TraceId
import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext
import java.util.*

/**
 * Created by hetiu 2018/1/9.<br/>
 */

const val X_TRACE_ID: String = "x-trace-id"

class TraceIdResponseHandler : Handler<RoutingContext> {

    override fun handle(ctx: RoutingContext) {
        val traceId = ctx.request().getHeader(X_TRACE_ID) ?: UUID.randomUUID().toString()
        ctx.put("traceId", TraceId(traceId))

        ctx.addHeadersEndHandler {
            ctx.response().putHeader(X_TRACE_ID, traceId)
        }

        ctx.next()
    }
}
