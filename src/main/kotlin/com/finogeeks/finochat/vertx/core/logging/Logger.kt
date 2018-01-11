package com.finogeeks.finochat.vertx.core.logging

import com.finogeeks.finochat.vertx.common.TraceId
import io.vertx.core.logging.Logger

/**
 * Created by hetiu 2018/1/10.<br/>
 */

const val formater: String = "{} - {}"

fun Logger.infoX(traceId: TraceId, message: Any) {
    this.info(formater, traceId.id, message)
}

fun Logger.infoX(traceId: TraceId, message: Any, t: Throwable) {
    this.info(formater, t, traceId.id, message)
}


fun Logger.debugX(traceId: TraceId, message: Any) {
    this.debug(formater, traceId.id, message)
}

fun Logger.debugX(traceId: TraceId, message: Any, t: Throwable) {
    this.debug(formater, t, traceId.id, message)
}


fun Logger.errorX(traceId: TraceId, message: Any) {
    this.error(formater, traceId.id, message)
}

fun Logger.errorX(traceId: TraceId, message: Any, t: Throwable) {
    this.error(formater, t, traceId.id, message)
}


fun Logger.warnX(traceId: TraceId, message: Any) {
    this.warn(formater, traceId.id, message)
}

fun Logger.warnX(traceId: TraceId, message: Any, t: Throwable) {
    this.warn(formater, t, traceId.id, message)
}


fun Logger.traceX(traceId: TraceId, message: Any) {
    this.trace(formater, traceId.id, message)
}

fun Logger.traceX(traceId: TraceId, message: Any, t: Throwable) {
    this.trace(formater, t, traceId.id, message)
}