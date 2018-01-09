package com.finogeeks.finochat.vertx.router

import com.finogeeks.finochat.vertx.handler.NaiveHandlerFactory
import io.vertx.ext.web.Router

/**
 * Created by hetiu 2018/1/9.<br/>
 */

fun Router.setBusinessRouter(handlerFactory: NaiveHandlerFactory) {
    this.get("/demo/rx").handler(handlerFactory.demoHandlerRx)
    this.get("/demo/co").handler(handlerFactory.demoHandlerCo)
}