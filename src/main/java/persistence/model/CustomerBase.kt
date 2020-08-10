package persistence.model

import java.time.LocalDate

interface CustomerBase {

    fun addCustomer(customer: Customer)

    fun customersWithExpiredCreditCardsAsOf(deadline: LocalDate): List<Customer>
}
