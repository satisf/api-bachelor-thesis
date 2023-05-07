package edu.satisf.googlerejoiner

import com.google.api.graphql.rejoiner.*
import com.google.common.util.concurrent.ListenableFuture
import edu.satisf.grpcinterfacejava11.*
import edu.satisf.grpcinterfacejava11.BankServiceGrpc.BankServiceFutureStub

class BankServiceSchemaModule: SchemaModule() {

    @Mutation("commissionTransfer")
    fun commissionTransfer(transferRequest: TransferRequest, bankServiceStub: BankServiceFutureStub): ListenableFuture<TransferResponse> {
        return bankServiceStub.commissionTransfer(transferRequest)
    }

    @Query("getBalance")
    fun getBalance(balanceRequest: BalanceRequest, bankServiceStub: BankServiceFutureStub): ListenableFuture<BalanceResponse> {
        return bankServiceStub.requestBalance(balanceRequest)
    }

    @Query("listPastTransfers")
    fun listPastTransfers(pastTransfersRequest: PastTransfersRequest, bankServiceStub: BankServiceFutureStub): ListenableFuture<PastTransfersResponse> {
        return bankServiceStub.listPastTransfers(pastTransfersRequest)
    }
}