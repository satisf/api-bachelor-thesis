package edu.satisf.googlerejoiner

import com.google.inject.AbstractModule

class GraphQlSchemaModule: AbstractModule() {

    override fun configure() {
        install(BankServiceSchemaModule())
    }
}