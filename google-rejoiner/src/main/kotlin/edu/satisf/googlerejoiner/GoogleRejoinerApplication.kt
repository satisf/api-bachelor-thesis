package edu.satisf.googlerejoiner

import com.google.api.graphql.rejoiner.SchemaProviderModule
import com.google.inject.Guice
import com.google.inject.Injector
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class GoogleRejoinerApplication {

    private final var injector: Injector = Guice.createInjector(
        SchemaProviderModule(),
        GraphQlSchemaModule()
    )

    fun main(args: Array<String>) {
        runApplication<GoogleRejoinerApplication>(*args)
    }
}