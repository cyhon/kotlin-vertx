package com.finogeeks.finochat.vertx.core

import com.finogeeks.finochat.vertx.handler.basic.TRACE_ID_CTX_KEY
import org.slf4j.MDC
import kotlin.coroutines.experimental.AbstractCoroutineContextElement
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.ContinuationInterceptor
import kotlin.coroutines.experimental.CoroutineContext

class TraceableCoroutineContext(private var traceId: String,
                                private val dispatcher: ContinuationInterceptor
) : AbstractCoroutineContextElement(ContinuationInterceptor), ContinuationInterceptor {

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> =
            dispatcher.interceptContinuation(Wrapper(continuation))

    inner class Wrapper<in T>(private val continuation: Continuation<T>) : Continuation<T> {
        private inline fun wrap(block: () -> Unit) {
            try {
                MDC.put(TRACE_ID_CTX_KEY, traceId)
                block()
            } finally {
                traceId = MDC.get(TRACE_ID_CTX_KEY)
            }
        }
        override val context: CoroutineContext get() = continuation.context
        override fun resume(value: T) = wrap { continuation.resume(value) }
        override fun resumeWithException(exception: Throwable) = wrap { continuation.resumeWithException(exception) }
    }
}