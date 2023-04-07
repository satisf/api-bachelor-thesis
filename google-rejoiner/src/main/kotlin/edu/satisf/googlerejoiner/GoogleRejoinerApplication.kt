package edu.satisf.googlerejoiner

import com.google.api.graphql.execution.GuavaListenableFutureSupport
import com.google.api.graphql.rejoiner.Schema
import com.google.api.graphql.rejoiner.SchemaProviderModule
import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.Key
import graphql.execution.instrumentation.Instrumentation
import graphql.schema.GraphQLSchema
import graphql.servlet.config.DefaultGraphQLSchemaProvider
import graphql.servlet.config.GraphQLSchemaProvider
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import edu.satisf.grpcinterface.BankServiceGrpc.BankServiceFutureStub


@SpringBootApplication
class GoogleRejoinerApplication {

    private final var injector: Injector = Guice.createInjector(
        SchemaProviderModule(),
        GrpcClientModule(),
        GraphQlSchemaModule()
    )

    fun main(args: Array<String>) {
        runApplication<GoogleRejoinerApplication>(*args)
    }

    @Bean
    fun schemaProvider(): GraphQLSchemaProvider? {
        val schema: GraphQLSchema = injector.getInstance(Key.get(GraphQLSchema::class.java, Schema::class.java))
        return DefaultGraphQLSchemaProvider(schema)
    }

    @Bean
    fun instrumentation(): Instrumentation? {
        return GuavaListenableFutureSupport.listenableFutureInstrumentation()
    }

    @Bean
    fun bankServiceFutureStub(): BankServiceFutureStub? {
        return injector.getInstance(BankServiceFutureStub::class.java)
    }
}