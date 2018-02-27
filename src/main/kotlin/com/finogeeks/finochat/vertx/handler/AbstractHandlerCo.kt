package com.finogeeks.finochat.vertx.handler

import com.finogeeks.finochat.vertx.core.TraceableCoroutineContext
import com.finogeeks.finochat.vertx.core.getTraceId
import com.finogeeks.finochat.vertx.dto.CommonResponse
import com.finogeeks.finochat.vertx.dto.ErrorCode
import com.finogeeks.finochat.vertx.router.json
import io.vertx.core.Handler
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.experimental.launch

/**
 * Created by hetiu 2017/11/20.<br/>
 */
abstract class AbstractHandlerCo : Handler<RoutingContext> {

    private val _log = LoggerFactory.getLogger("AbstractHandlerCo")

    abstract suspend fun call(context: RoutingContext): CommonResponse

    override fun handle(ctx: RoutingContext) {
        launch(TraceableCoroutineContext(ctx.getTraceId(), ctx.vertx().dispatcher())) {
            try {
                val respDTO = call(ctx)
                val response = ctx.response()
                response.statusCode = respDTO.status
                respDTO.header?.let { it.forEach { k, v -> response.putHeader(k, v) } }
                response.json()
                        .end(respDTO.toString())
            } catch (e: Exception) {
                _log.error(e.message, e)
                ctx.response()
                        .json()
                        .setStatusCode(500)
                        .end(ErrorCode.FC_ERROR.toString())
            }
        }
    }
}