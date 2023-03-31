package edu.satisf.grpcserverinterface

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GrpcServerInterfaceApplication

fun main(args: Array<String>) {
	runApplication<GrpcServerInterfaceApplication>(*args)
}
