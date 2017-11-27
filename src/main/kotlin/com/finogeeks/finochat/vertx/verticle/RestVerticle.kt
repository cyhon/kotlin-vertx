package com.finogeeks.finochat.vertx.verticle

import com.finogeeks.finochat.vertx.ApplicationModule
import com.finogeeks.finochat.vertx.handler.NaiveHandlerFactory
import com.finogeeks.finochat.vertx.router.BasicRouter
import com.google.inject.Guice
import io.vertx.core.AbstractVerticle
import io.vertx.core.logging.LoggerFactory
import kotlin.system.exitProcess

/**
 * Created by hetiu 2017/8/20.<br/>
 */
class RestVerticle : AbstractVerticle() {

    private val LOG = LoggerFactory.getLogger("RestVerticle")

    override fun start() {

        vertx.executeBlocking<NaiveHandlerFactory>({
            val injector = Guice.createInjector(ApplicationModule(vertx))
            injector.injectMembers(this)
            val handlerFactory = injector.getInstance(NaiveHandlerFactory::class.java)
            it.complete(handlerFactory)
        }) {
            if (it.succeeded()) {
                val router = BasicRouter(vertx, it.result())
                val port = config().getInteger("rest.server.http.port", 8080)
                vertx.createHttpServer().requestHandler({ router.accept(it) }).listen(port)
            } else {
                LOG.error(it.cause())
                exitProcess(-1)
            }
        }
    }
}