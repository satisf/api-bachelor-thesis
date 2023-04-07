package edu.satisf.googlerejoiner

import com.google.api.graphql.rejoiner.Mutation
import com.google.api.graphql.rejoiner.Query
import com.google.api.graphql.rejoiner.SchemaModule
import com.google.common.util.concurrent.ListenableFuture
import edu.satisf.grpcserver.TransferRequest
import edu.satisf.grpcserver.TransferResponse
import edu.satisf.grpcserver.BankAccountBalanceRequest
import edu.satisf.grpcserver.BalanceResponse
import edu.satisf.grpcserver.BankServiceGrpc.BankServiceFutureStub

class BankServiceSchemaModule(): SchemaModule() {

    @Mutation("commissionTransfer")
    fun commissionTransfer(transferRequest: TransferRequest, bankServiceStub: BankServiceFutureStub): ListenableFuture<TransferResponse> {
        return bankServiceStub.commissionTransfer(transferRequest)
    }

    @Query("getBalance")
    fun getBalance(bankAccountBalanceRequest: BankAccountBalanceRequest, bankServiceStub: BankServiceFutureStub): ListenableFuture<BalanceResponse> {
        return bankServiceStub.requestBalance(bankAccountBalanceRequest)
    }

}