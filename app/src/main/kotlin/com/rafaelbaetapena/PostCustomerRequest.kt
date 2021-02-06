package com.rafaelbaetapena

import io.micronaut.core.annotation.Introspected

@Introspected
class PostCustomerRequest(
    val name: String,
    @field:ValidCpf val document: String)