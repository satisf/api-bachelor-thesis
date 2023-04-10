package edu.satisf.grpcserver

import edu.satisf.grpcinterface.*
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService


@GrpcService
class BankServiceImpl: BankServiceGrpc.BankServiceImplBase() {

    override fun requestBalance(
        request: BankAccountBalanceRequest?,
        responseObserver: StreamObserver<BalanceResponse>
    ) {
        val response = BalanceResponse.newBuilder().setCurrentBalance(10.0f).build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun commissionTransfer(request: TransferRequest?, responseObserver: StreamObserver<TransferResponse>) {
        val response = TransferResponse.newBuilder().setSuccess(true).build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}