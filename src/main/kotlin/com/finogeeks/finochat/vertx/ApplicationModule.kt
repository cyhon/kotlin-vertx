package com.finogeeks.finochat.vertx

import com.finogeeks.finochat.vertx.core.exp.RxHelper
import com.finogeeks.finochat.vertx.handler.NaiveHandlerFactory
import com.google.inject.AbstractModule
import com.google.inject.name.Names
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import io.reactivex.Scheduler
import io.vertx.core.Vertx
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.Morphia


class ApplicationModule(private val vertx: Vertx) : AbstractModule() {

    override fun configure() {
        val config = vertx.orCreateContext.config()

        val datastore = Morphia().mapPackage("com.finogeeks.finochat.vertx.entity")
                .createDatastore(MongoClient(
                    MongoClientURI(config.getString("mongo.connstr"))),
                    config.getString("mongo.db"))
        datastore.ensureIndexes()
        bind(Datastore::class.java).toInstance(datastore)

        bind(Scheduler::class.java)
                .annotatedWith(Names.named("NonBlockingScheduler"))
                .toInstance(getNonBlockingScheduler())

        bind(NaiveHandlerFactory::class.java).asEagerSingleton()
    }

    fun getNonBlockingScheduler(): Scheduler {
        return RxHelper.scheduler(vertx)
    }
}
