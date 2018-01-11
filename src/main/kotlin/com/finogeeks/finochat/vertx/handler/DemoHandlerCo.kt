package com.finogeeks.finochat.vertx.handler

import com.finogeeks.finochat.vertx.common.TraceId
import com.finogeeks.finochat.vertx.core.logging.errorX
import com.finogeeks.finochat.vertx.core.logging.infoX
import com.finogeeks.finochat.vertx.dto.CommonResponse
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.RoutingContext

/**
 * Created by hetiu 2017/11/20.<br/>
 */

data class TestResponse(val test: String, val traceId: TraceId)

class DemoHandlerCo: AbstractHandlerCo() {

    private val LOG = LoggerFactory.getLogger(DemoHandlerCo::class.java)

    override suspend fun call(context: RoutingContext): CommonResponse {
        LOG.infoX(context.get("traceId"), "coroutine test")

        return CommonResponse(200, TestResponse("coroutines", context.get("traceId")))
    }
}