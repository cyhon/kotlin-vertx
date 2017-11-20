package com.finogeeks.finochat.vertx.common

import io.vertx.config.ConfigRetriever
import io.vertx.config.ConfigRetrieverOptions
import io.vertx.config.ConfigStoreOptions
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import java.net.Inet4Address

/**
 * Created by hetiu 2017/8/21.<br/>
 */
fun getConfig(vertx: Vertx): Future<JsonObject> {
    val envStore = ConfigStoreOptions().setType("env")
    val fileStore = ConfigStoreOptions()
            .setType("file")
            .setConfig(JsonObject().put("path", "config/application.json"))

    val options = ConfigRetrieverOptions()
            .addStore(fileStore).addStore(envStore)

    return ConfigRetriever.getConfigAsFuture(ConfigRetriever.create(vertx, options))
            .compose {
                if (it.getString("mongo.connstr", "").isEmpty()) {
                    val auth = it.getString("mongo.cluster.auth", "")
                    val port = it.getInteger("mongo.cluster.port", 27017)
                    val addrs = Inet4Address.getAllByName(it.getString("mongo.cluster.domain"))
                        .filter { it is Inet4Address }
                        .map { "${it.hostAddress}:${port}" }
                        .joinToString(",")
                    val connstr = if (auth.isEmpty()) "mongodb://${addrs}" else "mongodb://${auth}@${addrs}"
                    it.put("mongo.connstr", connstr)
                }
                Future.succeededFuture(it)
            }
}