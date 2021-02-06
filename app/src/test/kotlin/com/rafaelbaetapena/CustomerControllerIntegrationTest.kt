package com.rafaelbaetapena

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
internal class CustomerControllerIntegrationTest {

    @Inject
    @field:Client("/")
    private lateinit var client : RxHttpClient

    @Test
    fun `when try to get a customer by document expected returns the customer info` () {

        val request: HttpRequest<Any> = HttpRequest.GET("/customers/368.564.500-52")
        val body = client.toBlocking().retrieve(request)
        println(body)
        Assertions.assertNotNull(body)
        Assertions.assertEquals("368.564.500-52", body)
    }

    @Test
    fun `when try to get a customer with invalid document expected returns invalid CPF message` () {

        val request: HttpRequest<Any> = HttpRequest.GET("/customers/999.999.999-99")
        val responseException = Assertions.assertThrows(HttpClientResponseException::class.java) { client.toBlocking().retrieve(request) }
        Assertions.assertNotNull(responseException)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseException.status)
        Assertions.assertEquals("document: CPF is invalid", responseException.message)
    }

    @Test
    fun `when post a customer expected returns the name and document of the customer` () {

        val postCustomerRequest = PostCustomerRequest("João Silva", "368.564.500-52")
        val request: HttpRequest<Any> = HttpRequest.POST("/customers", postCustomerRequest)
        val body = client.toBlocking().retrieve(request)
        println(body)
        Assertions.assertNotNull(body)
        Assertions.assertEquals("João Silva 368.564.500-52", body)
    }
}