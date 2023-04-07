package edu.satisf.googlerejoiner

import com.google.inject.AbstractModule
import io.grpc.ManagedChannelBuilder
import edu.satisf.grpcinterface.BankServiceGrpc


class BankServiceGrpcClient: AbstractModule() {

    private val HOST = "localhost"
    private val PORT = 9090

    override fun configure() {
        val channel = ManagedChannelBuilder.forAddress(HOST, PORT).usePlaintext().build()
        bind(BankServiceGrpc.BankServiceFutureStub::class.java).toInstance(BankServiceGrpc.newFutureStub(channel))
        bind(BankServiceGrpc.BankServiceBlockingStub::class.java).toInstance(
            BankServiceGrpc.newBlockingStub(channel)
        )
    }
}