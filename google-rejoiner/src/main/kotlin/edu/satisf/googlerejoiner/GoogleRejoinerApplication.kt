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

    private final var injector: Injector = Guice.createInjector(
        SchemaProviderModule(),
        GrpcClientModule(),
        GraphQlSchemaModule()
    )


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
fun main(args: Array<String>) {
    runApplication<GoogleRejoinerApplication>(*args)
}