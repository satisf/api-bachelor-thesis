package edu.satisf.googlerejoiner

import com.google.api.graphql.execution.GuavaListenableFutureSupport
import com.google.api.graphql.rejoiner.Schema
import com.google.api.graphql.rejoiner.SchemaProviderModule
import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.Key
import edu.satisf.grpcinterface.BankServiceGrpc
import graphql.execution.instrumentation.Instrumentation
import graphql.kickstart.execution.config.DefaultGraphQLSchemaProvider
import graphql.kickstart.execution.config.GraphQLSchemaProvider
import graphql.schema.GraphQLSchema
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RejoinerConfiguration {


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
    fun bankServiceFutureStub(): BankServiceGrpc.BankServiceFutureStub? {
        return injector.getInstance(BankServiceGrpc.BankServiceFutureStub::class.java)
    }
}