package edu.satisf.springbootstarterweb.grpc

import edu.satisf.grpcinterface.*
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Service


@Service
class BankServiceGrpcService(
    @GrpcClient("BankService")
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