package com.rafaelbaetapena

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import javax.validation.Valid

@Controller("/customers")
@Validated
class CustomerController {

    @Get("/{document}")
    @Produces(MediaType.APPLICATION_JSON)
    fun get(
        @PathVariable(name = "document") @ValidCpf document: String ) : String {
        return document
    }

    @Post("/")
    @Produces(MediaType.APPLICATION_JSON)
    fun post(
        @Body @Valid request: PostCustomerRequest ) : String {
        return "${request.name} ${request.document}"
    }
}