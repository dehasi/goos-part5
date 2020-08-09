package jpa

import persistence.model.Customer
import persistence.model.CustomerBase
import java.time.LocalDateTime
import javax.persistence.EntityManager
import javax.persistence.Query

class PersistentCustomerBase(private val entityManager: EntityManager) : CustomerBase {

    override fun addCustomer(customer: Customer) {
        entityManager.persist(customer)
    }

    override fun customersWithExpiredCreditCardsAsOf(deadline: LocalDateTime): List<Customer> {
        val query: Query = entityManager.createQuery("""
            SELECT c from Customer c, CreditCardDetails d 
            WHERE d member of c.paymentMethods  
            AND d.expiryDate < :deadline""".trimIndent())
        query.setParameter("deadline", deadline)

        @Suppress("UNCHECKED_CAST")
        return query.resultList as List<Customer>
    }
}
