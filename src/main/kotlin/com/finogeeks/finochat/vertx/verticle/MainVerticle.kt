package com.finogeeks.finochat.vertx.verticle

import com.finogeeks.finochat.vertx.common.getConfig
import io.vertx.core.AbstractVerticle
import io.vertx.core.DeploymentOptions
import io.vertx.core.json.JsonObject

/**
 * Created by hetiu 2017/8/17.<br/>
 */


class MainVerticle : AbstractVerticle() {
    override fun start() {
        vertx.executeBlocking<JsonObject> ({event ->
            getConfig(vertx).setHandler { event.complete(it.result()) }
        }, {
            vertx.deployVerticle(RestVerticle(),
                DeploymentOptions().setConfig(it.result()).setWorker(true))
        })
    }
}