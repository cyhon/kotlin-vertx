package com.finogeeks.finochat.vertx.handler

import com.finogeeks.finochat.vertx.dto.CommonResponse
import com.finogeeks.finochat.vertx.dto.ErrorCode
import com.finogeeks.finochat.vertx.router.json
import com.google.inject.Inject
import com.google.inject.name.Named
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy
import io.vertx.core.Handler
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.RoutingContext

/**
 * Created by hetiu 2017/9/1.<br/>
 */

@Deprecated("rx-handler is deprecated due to lack of tracing support", ReplaceWith("AbstractHandlerCo"))
abstract class AbstractHandlerRx : Handler<RoutingContext> {

    private val LOG = LoggerFactory.getLogger("AbstractHandlerRx")

    @Inject
    @Named("NonBlockingScheduler")
    lateinit var vertxRxScheduler: Scheduler

    abstract fun call(context: RoutingContext): Single<CommonResponse>

    override fun handle(context: RoutingContext) {
        this.call(context)
                .subscribeOn(vertxRxScheduler)
                .subscribeBy(
                        onSuccess = {
                            val response = context.response()
                            response.statusCode = it.status
                            it.header?.let { it.forEach { k, v -> response.putHeader(k, v) } }
                            response.json().end(it.toString())
                        },
                        onError = {
                            LOG.error(it.message, it)
                            context.response().setStatusCode(500).json().end(ErrorCode.FC_ERROR.toString())
                        }
                )
    }
}