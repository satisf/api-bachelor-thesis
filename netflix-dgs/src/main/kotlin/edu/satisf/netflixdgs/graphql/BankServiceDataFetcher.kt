package edu.satisf.netflixdgs.graphql

import com.netflix.graphql.dgs.*
import edu.satisf.netflixdgs.grpc.BankServiceGrpcService
import edu.satisf.grpcinterface.*
import edu.satisf.netflixdgs.generated.types.*

@DgsComponent
class BankServiceDataFetcher(
    val bankServiceGrpcService: BankServiceGrpcService
) {

    @DgsQuery
    fun currentBalance(@InputArgument bankAccountBalanceRequest: BankAccountBalanceGqlRequest): BalanceGqlResponse =
        bankServiceGrpcService.requestBalance(bankAccountBalanceRequest.toGrpc())
            .toGql()

    @DgsMutation
    fun commissionTransfer(@InputArgument transferRequest: TransferGqlRequest): TransferGqlResponse =
        bankServiceGrpcService.commissionTransfer(transferRequest.toGrpc())
            .toGql()
}

fun TransferGqlRequest.toGrpc() = TransferRequest
    .newBuilder()
    .setTo(this.to)
    .setFrom(this.from)
    .setAmount(this.amount?.toGrpc())
    .build()

fun TransferResponse.toGql(): TransferGqlResponse = TransferGqlResponse(this.success)

fun BankAccountBalanceGqlRequest.toGrpc(): BankAccountBalanceRequest = BankAccountBalanceRequest
    .newBuilder()
    .setAccount(this.account)
    .build()

fun BalanceResponse.toGql() = BalanceGqlResponse(this.currentBalance.toDouble())

fun AmountGqlDto.toGrpc(): AmountDto = AmountDto
    .newBuilder()
    .setAmount(this.amount!!.toFloat())
    .setCurrency(this.currency?.toGrpc())
    .build()

fun edu.satisf.netflixdgs.generated.types.Currency.toGrpc(): edu.satisf.grpcinterface.Currency = when(this) {
    edu.satisf.netflixdgs.generated.types.Currency.EURO -> edu.satisf.grpcinterface.Currency.EURO
    edu.satisf.netflixdgs.generated.types.Currency.DOLLAR -> edu.satisf.grpcinterface.Currency.DOLLAR
}

