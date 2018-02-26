package com.finogeeks.finochat.vertx.core

import kotlin.coroutines.experimental.AbstractCoroutineContextElement
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.ContinuationInterceptor
import kotlin.coroutines.experimental.CoroutineContext

class TraceableCoroutineContext(private var traceId: String,
                                private val dispatcher: ContinuationInterceptor
) : AbstractCoroutineContextElement(ContinuationInterceptor), ContinuationInterceptor {

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> =
            dispatcher.interceptContinuation(Wrapper(continuation))

    inner class Wrapper<T>(private val continuation: Continuation<T>) : Continuation<T> {
        private inline fun wrap(block: () -> Unit) {
            try {
                TraceIdRepo.traceTL.set(traceId)

                println("ready to resume: threadId ${Thread.currentThread().id}")
                println("ready to resume: traceId $traceId")
                block()
            } finally {
                traceId = TraceIdRepo.traceTL.get()
            }
        }

        override val context: CoroutineContext get() = continuation.context
        override fun resume(value: T) = wrap { continuation.resume(value) }
        override fun resumeWithException(exception: Throwable) = wrap { continuation.resumeWithException(exception) }
    }
}