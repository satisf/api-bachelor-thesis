package edu.satisf.grpcserver

import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService


@GrpcService
class BankServiceImpl: BankServiceGrpc.BankServiceImplBase() {

    override fun balanceRequest(
        request: BankAccountBalanceRequest?,
        responseObserver: StreamObserver<BalanceResponse>
    ) {
        val response = BalanceResponse.newBuilder().setCurrentBalance(10.0).build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun requestTransfer(request: TransferRequest?, responseObserver: StreamObserver<TransferResponse>) {
        val response = TransferResponse.newBuilder().setSuccess(true).build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}