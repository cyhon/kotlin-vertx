package com.finogeeks.finochat.vertx.core.exp

import io.reactivex.Single
import io.vertx.core.Vertx
import io.vertx.core.shareddata.Lock
import io.vertx.core.shareddata.SharedData
import org.funktionale.option.Option
import org.funktionale.option.Option.*

object LockHelper {

    fun getLockWithTimeoutRx(vertx: Vertx, name: String, timeout: Long): Single<Option<Lock>> {
        return getLockWithTimeoutRx(vertx.sharedData(), name, timeout)
    }

    fun getLockWithTimeoutRx(sd: SharedData, name: String, timeout: Long): Single<Option<Lock>> {
        return Single.create { subscriber ->
            sd.getLockWithTimeout(name, timeout, { res ->
                if (res.succeeded()) {
                    // Got the lock!
                    val lock = res.result()
                    subscriber.onSuccess(Some(lock))
                } else {
                    // Failed to get lock
                    subscriber.onSuccess(None)
                }
            })
        }
    }
}