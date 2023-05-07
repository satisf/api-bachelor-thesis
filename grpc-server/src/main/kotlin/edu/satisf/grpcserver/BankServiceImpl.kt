package edu.satisf.grpcserver

import edu.satisf.grpcinterface.*
import io.grpc.stub.StreamObserver
import mu.KotlinLogging
import net.devh.boot.grpc.server.service.GrpcService

private val logger = KotlinLogging.logger {  }

@GrpcService
class BankServiceImpl: BankServiceGrpc.BankServiceImplBase() {

    override fun requestBalance(
        request: BalanceRequest?,
        responseObserver: StreamObserver<BalanceResponse>
    ) {
        logger.info("requestBalance received for account ${request?.account}")
        val response = BalanceResponse.newBuilder().setCurrentBalance(10.0f).build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun listPastTransfers(
        request: PastTransfersRequest?,
        responseObserver: StreamObserver<PastTransfersResponse>
    ) {
        logger.info("listPastTransfers Request received for account ${request?.account}")
        val response = PastTransfersResponse.newBuilder().addTransfers(
            Transfer.newBuilder()
            .setFrom("me")
            .setTo("you")
            .setReference("pizza last night")
            .setAmount(12.50F)
            .setCurrency(Currency.EURO))
            .build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun commissionTransfer(request: TransferRequest?, responseObserver: StreamObserver<TransferResponse>) {
        logger.info("commissionTransfer received from ${request?.requestedTransfer?.from} to ${request?.requestedTransfer?.to} with reference ${request?.requestedTransfer?.reference} amount: ${request?.requestedTransfer?.amount} ${request?.requestedTransfer?.currency}")
        val response = TransferResponse.newBuilder().setSuccess(true).build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}