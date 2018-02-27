package com.finogeeks.finochat.vertx.client

import com.finogeeks.finochat.vertx.core.traceable
import com.finogeeks.finochat.vertx.core.withTrace
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
                .withTrace()
                .timeout(timeout)
                .send(traceable {
                    if (it.succeeded()) {
                        val response = it.result()
                        val result = when (response.statusCode()) {
                            200 -> {
                                logger.info("成功获取行情信息...")
                                response.bodyAsString("GBK")
                            }
                            else -> "error occur not 200!"
                        }
                        future.complete(result)
                    } else {
                        future.complete(it.cause().message)
                    }
                })

        return future
    }
}