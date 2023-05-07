package edu.satisf.netflixdgs.grpc

import org.springframework.stereotype.Service
import edu.satisf.grpcinterface.*
import edu.satisf.netflixdgs.generated.types.ListPastTransfersGqlRequest

@Service
class BankServiceGrpcService(
    private val bankServiceStub: BankServiceGrpc.BankServiceBlockingStub
) {
    fun commissionTransfer(transferRequest: TransferRequest): TransferResponse {
        return bankServiceStub.commissionTransfer(transferRequest)
    }

    fun listPastTransfers(pastTransfersRequest: PastTransfersRequest): PastTransfersResponse {
        return bankServiceStub.listPastTransfers(pastTransfersRequest)
    }
    fun requestBalance(balanceRequest: BalanceRequest): BalanceResponse {
        return bankServiceStub.requestBalance(balanceRequest)
    }
}