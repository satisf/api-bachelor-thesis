package edu.satisf.googlerejoiner

import com.google.api.graphql.execution.GuavaListenableFutureSupport
import com.google.api.graphql.rejoiner.Schema
import com.google.api.graphql.rejoiner.SchemaProviderModule
import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.Key
import graphql.execution.instrumentation.Instrumentation
import graphql.schema.GraphQLSchema
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import edu.satisf.grpcinterface.BankServiceGrpc.BankServiceFutureStub
import graphql.kickstart.execution.config.DefaultGraphQLSchemaProvider
import graphql.kickstart.execution.config.GraphQLSchemaProvider


@SpringBootApplication
class GoogleRejoinerApplication {
}
fun main(args: Array<String>) {
    runApplication<GoogleRejoinerApplication>(*args)
}