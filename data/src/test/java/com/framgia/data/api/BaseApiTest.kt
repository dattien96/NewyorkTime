package com.framgia.data.api

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import java.io.IOException

abstract class BaseApiTest {
    lateinit var mockWebServer: MockWebServer

    @Before
    @Throws
    open fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    @Throws(IOException::class)
    fun stopService() {
        mockWebServer.shutdown()
    }
}