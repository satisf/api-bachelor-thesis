package edu.satisf.netflixdgs.grpc

import org.springframework.stereotype.Service
import edu.satisf.grpcinterface.BankServiceGrpc
import edu.satisf.grpcinterface.BankAccountBalanceRequest
import edu.satisf.grpcinterface.AmountDto
import edu.satisf.grpcinterface.BalanceResponse
import edu.satisf.grpcinterface.Currency
import edu.satisf.grpcinterface.TransferRequest
import edu.satisf.grpcinterface.TransferResponse

@Service
class BankServiceGrpcService(
    private val bankServiceStub: BankServiceGrpc.BankServiceBlockingStub
) {

    fun commissionTransfer(transferRequest: TransferRequest): TransferResponse {
        return bankServiceStub.commissionTransfer(transferRequest)
    }

    fun commissionTransfer(from: String, to: String, amount: Float, currency: Currency): Boolean =
        commissionTransfer(buildTransferRequest(from, to, amount, currency)).success

    fun requestBalance(bankAccountBalanceRequest: BankAccountBalanceRequest): BalanceResponse {
        return bankServiceStub.requestBalance(bankAccountBalanceRequest)
    }

    fun requestBalance(accountName: String): Float = requestBalance(buildBalanceRequest(accountName)).currentBalance

    fun buildBalanceRequest(accountName: String) =
        BankAccountBalanceRequest
            .newBuilder()
            .setAccount(accountName)
            .build()

    fun buildTransferRequest(from: String, to: String, amount: Float, currency: Currency) =
        TransferRequest
            .newBuilder()
            .setAmount(
                AmountDto
                    .newBuilder()
                    .setAmount(amount)
                    .setCurrency(currency)
                    .build())
            .build()
}