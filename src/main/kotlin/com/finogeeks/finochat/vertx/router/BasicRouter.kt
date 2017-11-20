package com.finogeeks.finochat.vertx.router

/**
 * Created by hetiu 2017/8/19.<br/>
 */
import com.finogeeks.finochat.vertx.handler.NaiveHandlerFactory
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler

fun BasicRouter(vertx: Vertx, handlerFactory: NaiveHandlerFactory) : Router {
    val router = Router.router(vertx)
    router.route().handler(BodyHandler.create())

    router.get("/demo/rx").handler(handlerFactory.demoHandlerRx)

    return router
}

fun HttpServerResponse.html() : HttpServerResponse {
    return this.putHeader("content-type","text/html")
}

fun HttpServerResponse.json() : HttpServerResponse {
    return this.putHeader("content-type","application/json; charset=utf-8")
}