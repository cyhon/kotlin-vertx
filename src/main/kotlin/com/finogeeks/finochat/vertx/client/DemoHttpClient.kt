package com.finogeeks.finochat.vertx.client

import com.google.inject.Inject
import io.vertx.core.Future
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.client.WebClient

class DemoHttpClient @Inject constructor(private val client: WebClient,
                                         private val baseUrl: String) {

    private val timeout: Long = 3000
    private val logger = LoggerFactory.getLogger("DemoHttpClient")

    fun getQuote(stockCode: String): Future<String> {

        val future = Future.future<String>()

        client.getAbs("$baseUrl/list=$stockCode")
                .timeout(timeout)
                .send {
                    if (it.succeeded()) {
                        val response = it.result()
                        val result = when (response.statusCode()) {
                            200 -> response.bodyAsString("GBK")
                            else -> "error occur not 200!"
                        }
                        future.complete(result)
                    } else {
                        future.complete(it.cause().message)
                    }
                }

        return future
    }
}