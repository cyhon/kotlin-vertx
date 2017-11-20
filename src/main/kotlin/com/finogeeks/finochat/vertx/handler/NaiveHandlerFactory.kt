package com.finogeeks.finochat.vertx.handler


import com.google.inject.Inject

class NaiveHandlerFactory {

    @Inject lateinit var demoHandlerRx: DemoHandlerRx
    @Inject lateinit var demoHandlerCo: DemoHandlerCo
}
