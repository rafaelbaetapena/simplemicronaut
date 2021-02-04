package com.rafaelbaetapena

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Produces

@Controller("/hello-world")
class HelloWorldController {

    @Get("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getHelloWorld(
        @PathVariable(name = "name") name: String ) : String {
        return "Hello $name"
    }
}