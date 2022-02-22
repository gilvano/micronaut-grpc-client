package com.gilvano

import io.micronaut.runtime.Micronaut.*

suspend fun main(args: Array<String>) {

	println("Hello World!")
	val service = GrpcClientService()
//	service.saveUser()
	service.saveUserStream()

	build()
	    .args(*args)
		.packages("com.gilvano")
		.start()
}

