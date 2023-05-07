package edu.satisf.springbootstarterweb.graphql

import edu.satisf.grpcinterface.*
import edu.satisf.springbootstarterweb.grpc.BankServiceGrpcService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import org.springframework.graphql.data.method.annotation.QueryMapping;
import java.lang.Exception

@Controller
class BankController(
    private val bankServiceGrpcService: BankServiceGrpcService
) {

    @QueryMapping
    fun currentBalance(@Argument balanceRequest: BalanceGqlRequest): BalanceGqlResponse =
        bankServiceGrpcService.requestBalance(balanceRequest.toGrpc())
            .toGql()

    @QueryMapping
    fun listPastTransfers(@Argument pastTransfersRequest: PastTransfersGqlRequest): PastTransfersGqlResponse =
        bankServiceGrpcService.listPastTransfers(pastTransfersRequest.toGrpc())
            .toGql()

    @MutationMapping
    fun commissionTransfer(@Argument transferRequest: TransferGqlRequest): TransferGqlResponse =
        bankServiceGrpcService.commissionTransfer(transferRequest.toGrpc())
            .toGql()
}

data class Transfer (
    val from: String,
    val to: String,
    val reference: String,
    val amount: Float,
    val currency: Currency
)
data class TransferGqlRequest(
    val requestedTransfer: Transfer
)
fun TransferGqlRequest.toGrpc(): TransferRequest = TransferRequest
    .newBuilder()
    .setRequestedTransfer(
        edu.satisf.grpcinterface.Transfer
            .newBuilder()
            .setFrom(this.requestedTransfer.from)
            .setTo(this.requestedTransfer.to)
            .setReference(this.requestedTransfer.reference)
            .setAmount(this.requestedTransfer.amount)
            .setCurrency(this.requestedTransfer.currency.toGrpc())
    ).build()

data class TransferGqlResponse(
    val success: Boolean
)

fun TransferResponse.toGql(): TransferGqlResponse = TransferGqlResponse(this.success)

data class PastTransfersGqlRequest(
    val account: String
)

fun PastTransfersGqlRequest.toGrpc(): PastTransfersRequest = PastTransfersRequest
    .newBuilder()
    .setAccount(this.account)
    .build()

data class PastTransfersGqlResponse(
    val transfers: List<Transfer>
)

fun PastTransfersResponse.toGql(): PastTransfersGqlResponse = PastTransfersGqlResponse(this.transfersList.map { Transfer(it.from, it.to, it.reference, it.amount, it.currency.toGql()) })

data class BalanceGqlRequest(
    val account: String
)
fun BalanceGqlRequest.toGrpc(): BalanceRequest = BalanceRequest
    .newBuilder()
    .setAccount(this.account)
    .build()

data class BalanceGqlResponse(
    val currentBalance: Float
)

fun BalanceResponse.toGql() = BalanceGqlResponse(this.currentBalance)


enum class Currency {
    EURO,
    DOLLAR
}
fun Currency.toGrpc(): edu.satisf.grpcinterface.Currency = when(this) {
    Currency.EURO -> edu.satisf.grpcinterface.Currency.EURO
    Currency.DOLLAR -> edu.satisf.grpcinterface.Currency.DOLLAR
}

fun edu.satisf.grpcinterface.Currency.toGql(): Currency = when(this) {
    edu.satisf.grpcinterface.Currency.EURO -> Currency.EURO
    edu.satisf.grpcinterface.Currency.DOLLAR -> Currency.DOLLAR
    else -> throw Exception("unknown currency $this")
}