package grpc

import org.springframework.stereotype.Service
import net.devh.boot.grpc.client.inject.GrpcClient
import edu.satisf.grpcinterface.BankServiceGrpc
import edu.satisf.grpcinterface.BankAccountBalanceRequest
import edu.satisf.grpcinterface.AmmountDto
import edu.satisf.grpcinterface.Currency
import edu.satisf.grpcinterface.TransferRequest

@Service
class BankServiceGrpcService {

    @GrpcClient("BankService")
    private lateinit var bankServiceStub: BankServiceGrpc.BankServiceBlockingStub

    fun commissionTransfer(from: String, to: String, amount: Float, currency: Currency ): Boolean {
        val amountDto = AmmountDto.newBuilder().setAmount(amount).setCurrency(currency).build()
        val transferRequest = TransferRequest.newBuilder().setAmount(amountDto).build()
        return bankServiceStub.commissionTransfer(transferRequest).success
    }

    fun requestBalance(accountName: String): Double {
        val bankAccountBalanceRequest = BankAccountBalanceRequest.newBuilder().setAccount(accountName).build()
        return bankServiceStub.requestBalance(bankAccountBalanceRequest).currentBalance
    };
}