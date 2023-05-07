package edu.satisf.netflixdgs.graphql

import com.netflix.graphql.dgs.*
import com.netflix.graphql.dgs.exceptions.DgsInvalidInputArgumentException
import edu.satisf.netflixdgs.grpc.BankServiceGrpcService
import edu.satisf.grpcinterface.*
import edu.satisf.grpcinterface.Transfer
import edu.satisf.netflixdgs.generated.types.*

@DgsComponent
class BankServiceDataFetcher(
    val bankServiceGrpcService: BankServiceGrpcService
) {

    @DgsQuery
    fun currentBalance(@InputArgument balanceRequest: BalanceGqlRequest): BalanceGqlResponse =
        bankServiceGrpcService.requestBalance(balanceRequest.toGrpc())
            .toGql()

    @DgsQuery
    fun listPastTransfers(@InputArgument pastTransfersRequest: PastTransfersGqlRequest): PastTransfersGqlResponse =
        bankServiceGrpcService.listPastTransfers(pastTransfersRequest.toGrpc())
            .toGql()

    @DgsMutation
    fun commissionTransfer(@InputArgument transferRequest: TransferGqlRequest): TransferGqlResponse =
        bankServiceGrpcService.commissionTransfer(transferRequest.toGrpc())
            .toGql()
}

fun TransferGqlRequest.toGrpc(): TransferRequest = TransferRequest
    .newBuilder()
    .setRequestedTransfer(
        Transfer
            .newBuilder()
            .setFrom(this.requestedTransfer?.from)
            .setTo(this.requestedTransfer?.to)
            .setReference(this.requestedTransfer?.reference)
            .setAmount(this.requestedTransfer?.amount!!.toFloat())
            .setCurrency(this.requestedTransfer.currency?.toGrpc())
    ).build()

fun TransferResponse.toGql(): TransferGqlResponse = TransferGqlResponse(this.success)

fun PastTransfersGqlRequest.toGrpc(): PastTransfersRequest = PastTransfersRequest
    .newBuilder()
    .setAccount(this.account)
    .build()

fun PastTransfersResponse.toGql(): PastTransfersGqlResponse = PastTransfersGqlResponse(
    this.transfersList.map {
        edu.satisf.netflixdgs.generated.types.Transfer(
            it.from,
            it.to,
            it.reference,
            it.amount.toDouble(),
            it.currency.toGql()
        )
    })

fun BalanceGqlRequest.toGrpc(): BalanceRequest = BalanceRequest
    .newBuilder()
    .setAccount(this.account)
    .build()

fun BalanceResponse.toGql(): BalanceGqlResponse = BalanceGqlResponse(this.currentBalance.toDouble())

fun edu.satisf.netflixdgs.generated.types.Currency.toGrpc(): edu.satisf.grpcinterface.Currency = when(this) {
    edu.satisf.netflixdgs.generated.types.Currency.EURO -> edu.satisf.grpcinterface.Currency.EURO
    edu.satisf.netflixdgs.generated.types.Currency.DOLLAR -> edu.satisf.grpcinterface.Currency.DOLLAR
}

fun edu.satisf.grpcinterface.Currency.toGql(): edu.satisf.netflixdgs.generated.types.Currency = when(this) {
    edu.satisf.grpcinterface.Currency.EURO -> edu.satisf.netflixdgs.generated.types.Currency.EURO
    edu.satisf.grpcinterface.Currency.DOLLAR -> edu.satisf.netflixdgs.generated.types.Currency.DOLLAR
    else -> throw DgsInvalidInputArgumentException("unknown currency $this")
}

