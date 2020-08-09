package persistence.model

import java.time.LocalDateTime

interface CustomerBase {

    fun addCustomer(user: Customer)

    fun customersWithExpiredCreditCardsAsOf(deadline: LocalDateTime): List<Customer>
}
