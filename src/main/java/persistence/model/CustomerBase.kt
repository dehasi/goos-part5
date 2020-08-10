package persistence.model

import java.time.LocalDateTime

interface CustomerBase {

    fun addCustomer(customer: Customer)

    fun customersWithExpiredCreditCardsAsOf(deadline: LocalDateTime): List<Customer>
}
