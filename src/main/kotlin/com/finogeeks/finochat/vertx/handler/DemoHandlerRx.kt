package com.finogeeks.finochat.vertx.handler

import com.finogeeks.finochat.vertx.dto.CommonResponse
import io.reactivex.Single
import io.vertx.core.json.JsonObject
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.RoutingContext

/**
 * Created by hetiu 2017/11/20.<br/>
 */
class DemoHandlerRx: AbstractHandlerRx() {

    private val LOG = LoggerFactory.getLogger("DemoHandlerRx")

    override fun call(context: RoutingContext): Single<CommonResponse> {
        LOG.info("rx test")

        return Single.just(CommonResponse(200, TestResponse("rx", context.get("traceId"))))
    }
}