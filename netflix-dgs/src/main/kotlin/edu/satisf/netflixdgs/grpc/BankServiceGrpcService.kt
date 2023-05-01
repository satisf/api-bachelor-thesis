package edu.satisf.netflixdgs.grpc

import org.springframework.stereotype.Service
import edu.satisf.grpcinterface.*

@Service
class BankServiceGrpcService(
    private val bankServiceStub: BankServiceGrpc.BankServiceBlockingStub
) {
    fun commissionTransfer(transferRequest: TransferRequest): TransferResponse {
        return bankServiceStub.commissionTransfer(transferRequest)
    }
    fun requestBalance(bankAccountBalanceRequest: BankAccountBalanceRequest): BalanceResponse {
        return bankServiceStub.requestBalance(bankAccountBalanceRequest)
    }
}