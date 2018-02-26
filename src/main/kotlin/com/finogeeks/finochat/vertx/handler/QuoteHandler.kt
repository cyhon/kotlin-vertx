package com.finogeeks.finochat.vertx.handler

import com.finogeeks.finochat.vertx.client.DemoHttpClient
import com.finogeeks.finochat.vertx.common.TraceId
import com.finogeeks.finochat.vertx.core.logging.infoX
import com.finogeeks.finochat.vertx.dto.CommonResponse
import com.google.inject.Inject
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.await

/**
 * Created by hetiu 2017/11/20.<br/>
 */

data class QuoteResponse(val quoteRes: String, val traceId: TraceId)

class QuoteHandlerCo @Inject constructor(private val quoteCli: DemoHttpClient) : AbstractHandlerCo() {

    private val LOG = LoggerFactory.getLogger(QuoteHandlerCo::class.java)

    override suspend fun call(context: RoutingContext): CommonResponse {
        LOG.infoX(context.get("traceId"), "coroutine test")

        val quoteResult = quoteCli.getQuote("sh600300").await()

        return CommonResponse(200, QuoteResponse(quoteResult, context.get("traceId")))
    }
}