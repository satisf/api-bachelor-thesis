package edu.satisf.springbootstarterweb

import edu.satisf.grpcinterface.Currency
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController()
class BankController {

    @GetMapping("/{account}/balance")
    fun getBalanceForAccount(@PathVariable account: String): Float {
        return 5.0F
    }

    @PostMapping("/{account}/transfer")
    fun requestTransfer(@PathVariable account: String, @RequestBody transferRequestBody: RequestTransferRequestBody): Boolean {
        val currency = transferRequestBody.currency
        return currency == Currency.EURO
    }
}

data class RequestTransferRequestBody(
    val to: String,
    val amount: Float,
    val currency: Currency
)