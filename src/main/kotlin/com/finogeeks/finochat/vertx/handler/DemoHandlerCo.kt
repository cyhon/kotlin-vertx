package com.finogeeks.finochat.vertx.handler

import com.finogeeks.finochat.vertx.dto.CommonResponse
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext

/**
 * Created by hetiu 2017/11/20.<br/>
 */
class DemoHandlerCo: AbstractHandlerCo() {

    override suspend fun call(context: RoutingContext): CommonResponse {
        return CommonResponse(200, JsonObject().put("test", "coroutines"))
    }
}