package com.github.carlwilk32

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.util.*

fun main(args: Array<String>): Unit = EngineMain.main(args)


fun Application.module() {
    val githubToken = environment.config.property("github.token").getString()
    val githubUsername = environment.config.property("github.user").getString()

    val client = HttpClient() { followRedirects = false }

    intercept(ApplicationCallPipeline.Call) {
        val response = client.request("https://maven.pkg.github.com/${githubUsername}/pkg${call.request.uri}") {
            headers {
                append(HttpHeaders.Authorization, "token $githubToken")
                append(HttpHeaders.Host, "maven.pkg.github.com")
            }
        }
        val location = response.headers[HttpHeaders.Location]
        location?.also { call.respondRedirect(it) } ?: call.respondBytes(
            response.bodyAsChannel().toByteArray(), response.contentType(), response.status
        )
    }
}