package com.finogeeks.finochat.vertx.handler

import com.finogeeks.finochat.vertx.dto.CommonResponse
import io.reactivex.Single
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext

/**
 * Created by hetiu 2017/11/20.<br/>
 */
class DemoHandlerRx: AbstractHandlerRx() {

    override fun call(context: RoutingContext): Single<CommonResponse> {
        return Single.just(CommonResponse(200, JsonObject().put("test", "rx")))
    }
}