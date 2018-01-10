package com.finogeeks.finochat.vertx.core.logging

import io.vertx.core.logging.Logger
import org.apache.logging.log4j.ThreadContext
import sun.rmi.runtime.Log

/**
 * Created by hetiu 2018/1/10.<br/>
 */

fun Logger.setTraceId(traceId: String): Logger {
    ThreadContext.put("traceId", traceId)
    return this
}