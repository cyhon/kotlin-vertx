package com.finogeeks.finochat.vertx.router

/**
 * Created by hetiu 2017/8/19.<br/>
 */
import com.finogeeks.finochat.vertx.dto.ErrorCode
import com.finogeeks.finochat.vertx.handler.NaiveHandlerFactory
import com.finogeeks.finochat.vertx.handler.basic.TraceIdHandler
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.LoggerFormat
import io.vertx.ext.web.handler.LoggerHandler

fun basicRouter(vertx: Vertx, handlerFactory: NaiveHandlerFactory) : Router {
    val router = Router.router(vertx)

    router.route().handler(LoggerHandler.create(LoggerFormat.TINY))
    router.route().handler(BodyHandler.create())
    router.route().handler(TraceIdHandler())
    router.route().last().handler { ctx ->
        ctx.response()
            .setStatusCode(404)
            .json()
            .end(ErrorCode.FC_NOT_FOUND.toString())
    }

    // 业务路由
    router.setBusinessRouter(handlerFactory)
    return router
}

fun HttpServerResponse.json() : HttpServerResponse {
    return this.putHeader("content-type","application/json; charset=utf-8")
}