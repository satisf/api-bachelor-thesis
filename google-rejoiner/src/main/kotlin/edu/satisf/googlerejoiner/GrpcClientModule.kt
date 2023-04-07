package edu.satisf.googlerejoiner

import com.google.inject.AbstractModule

class GrpcClientModule: AbstractModule() {

    override fun configure() {
        install(BankServiceGrpcClient())
    }
}