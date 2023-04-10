package edu.satisf.netflixdgs.graphql

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import edu.satisf.netflixdgs.grpc.BankServiceGrpcService
import edu.satisf.grpcinterface.*

@DgsComponent
class BankServiceDataFetcher(
    val bankServiceGrpcService: BankServiceGrpcService
) {

    @DgsQuery
    fun currentBalance(@InputArgument bankAccountBalanceRequest: BankAccountBalanceGqlRequest): BalanceGqlResponse =
        bankServiceGrpcService.requestBalance(bankAccountBalanceRequest.toGrpc())
            .toGql()

    @DgsMutation
    fun commissionTransfer(@InputArgument transferGqlRequest: TransferGqlRequest): TransferGqlResponse =
        bankServiceGrpcService.commissionTransfer(transferGqlRequest.toGrpc())
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

