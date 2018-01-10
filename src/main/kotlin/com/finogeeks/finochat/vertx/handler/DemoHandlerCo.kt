package com.finogeeks.finochat.vertx.handler

import com.finogeeks.finochat.vertx.dto.CommonResponse
import io.vertx.core.json.JsonObject
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.RoutingContext

/**
 * Created by hetiu 2017/11/20.<br/>
 */

data class TestResponse(val test: String, val traceId: String)

class DemoHandlerCo: AbstractHandlerCo() {

    private val LOG = LoggerFactory.getLogger("DemoHandlerCo")

    override suspend fun call(context: RoutingContext): CommonResponse {
        LOG.info("coroutine test")

        return CommonResponse(200, TestResponse("coroutines", context.get("traceId")))
    }
}