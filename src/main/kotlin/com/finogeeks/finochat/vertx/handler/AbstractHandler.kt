package com.finogeeks.finochat.vertx.handler

import com.finogeeks.finochat.vertx.dto.ErrorCode
import com.finogeeks.finochat.vertx.dto.CommonResponse
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

abstract class AbstractHandler : Handler<RoutingContext> {

    private val LOG = LoggerFactory.getLogger("AbstractHandler")

    @Inject @Named("NonBlockingScheduler") lateinit var vertxRxScheduler: Scheduler

    abstract fun call(context: RoutingContext): Single<CommonResponse>

    override fun handle(context: RoutingContext) {
        this.call(context)
            .subscribeOn(vertxRxScheduler)
            .subscribeBy(
                onSuccess = { context.response().setStatusCode(it.status).end(it.toString()) },
                onError = {
                    LOG.error(it.message, it)
                    context.response().setStatusCode(500).end(ErrorCode.FC_ERROR.toString())
                }
            )
    }
}