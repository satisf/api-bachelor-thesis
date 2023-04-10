package edu.satisf.springbootstarterweb

import edu.satisf.grpcinterface.Currency
import edu.satisf.springbootstarterweb.grpc.BankServiceGrpcService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController()
class BankController(
    private val bankServiceGrpcService: BankServiceGrpcService
) {

    @GetMapping("/{account}/balance")
    fun getBalanceForAccount(@PathVariable account: String): Float {
        return bankServiceGrpcService.requestBalance(account)
    }

    @PostMapping("/{account}/transfer")
    fun requestTransfer(@PathVariable account: String, @RequestBody transferRequestBody: RequestTransferRequestBody): Boolean {
        return bankServiceGrpcService.commissionTransfer(account, transferRequestBody.to, transferRequestBody.amount, transferRequestBody.currency)
    }
}

data class RequestTransferRequestBody(
    val to: String,
    val amount: Float,
    val currency: Currency
)