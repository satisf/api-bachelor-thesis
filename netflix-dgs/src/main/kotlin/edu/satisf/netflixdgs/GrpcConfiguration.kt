package edu.satisf.netflixdgs

import edu.satisf.grpcinterface.BankServiceGrpc
import net.devh.boot.grpc.client.inject.GrpcClient
import net.devh.boot.grpc.client.inject.GrpcClientBean
import org.springframework.context.annotation.Configuration

@GrpcClientBean(
    beanName = "bankServiceStub",
    clazz = BankServiceGrpc.BankServiceBlockingStub::class,
    client = GrpcClient(value = "BankServiceGrpcService"),
)
@Configuration
class GrpcConfiguration