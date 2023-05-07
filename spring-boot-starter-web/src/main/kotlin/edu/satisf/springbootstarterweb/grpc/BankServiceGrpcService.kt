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

    fun listPastTransfers(pastTransfersRequest: PastTransfersRequest): PastTransfersResponse{
        return bankServiceStub.listPastTransfers(pastTransfersRequest)
    }

    fun requestBalance(balanceRequest: BalanceRequest): BalanceResponse {
        return bankServiceStub.requestBalance(balanceRequest)
    }
}