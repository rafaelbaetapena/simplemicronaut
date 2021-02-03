package com.rafaelbaetapena

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("com.rafaelbaetapena")
		.start()
}

