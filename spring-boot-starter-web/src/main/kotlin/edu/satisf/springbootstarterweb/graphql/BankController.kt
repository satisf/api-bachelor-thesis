package edu.satisf.springbootstarterweb.graphql

import edu.satisf.grpcinterface.*
import edu.satisf.springbootstarterweb.grpc.BankServiceGrpcService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import org.springframework.graphql.data.method.annotation.QueryMapping;

@Controller
class BankController(
    private val bankServiceGrpcService: BankServiceGrpcService
) {

    @QueryMapping
    fun currentBalance(@Argument bankAccountBalanceRequest: BankAccountBalanceGqlRequest): BalanceGqlResponse =
        bankServiceGrpcService.requestBalance(bankAccountBalanceRequest.toGrpc())
            .toGql()

    @MutationMapping
    fun commissionTransfer(@Argument transferRequest: TransferGqlRequest): TransferGqlResponse =
        bankServiceGrpcService.commissionTransfer(transferRequest.toGrpc())
            .toGql()
}


data class TransferGqlRequest (
    val from: String,
    val to: String,
    val amount: AmountGqlDto
)
fun TransferGqlRequest.toGrpc() = TransferRequest
    .newBuilder()
    .setTo(this.to)
    .setFrom(this.from)
    .setAmount(this.amount.toGrpc())
    .build()

data class TransferGqlResponse(
    val success: Boolean
)
fun TransferResponse.toGql(): TransferGqlResponse = TransferGqlResponse(this.success)

data class BankAccountBalanceGqlRequest(
    val account: String
)
fun BankAccountBalanceGqlRequest.toGrpc(): BankAccountBalanceRequest = BankAccountBalanceRequest
    .newBuilder()
    .setAccount(this.account)
    .build()

data class BalanceGqlResponse(
    val currentBalance: Float
)
fun BalanceResponse.toGql() = BalanceGqlResponse(this.currentBalance)

data class AmountGqlDto (
    val amount: Float,
    val currency: Currency
)
fun AmountGqlDto.toGrpc(): AmountDto = AmountDto
    .newBuilder()
    .setAmount(this.amount)
    .setCurrency(this.currency.toGrpc())
    .build()

enum class Currency {
    EURO,
    DOLLAR
}
fun Currency.toGrpc(): edu.satisf.grpcinterface.Currency = when(this) {
    Currency.EURO -> edu.satisf.grpcinterface.Currency.EURO
    Currency.DOLLAR -> edu.satisf.grpcinterface.Currency.DOLLAR
}

