package persistence

import jpa.PersistentCustomerBase
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Test
import persistence.builders.CreditCardDetailsBuilder.Companion.aCreditCard
import persistence.builders.CustomerBuilder
import persistence.builders.CustomerBuilder.Companion.aCustomer
import persistence.model.Customer
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.persistence.Persistence


class PersistentCustomerBaseTest {

    private val factory = Persistence.createEntityManagerFactory("GOOS")
    private var entityManager = factory.createEntityManager()
    private val transactor = JPATransactor(entityManager)

    private val customerBase = PersistentCustomerBase(entityManager)

    @Before fun cleanDatabase() {
        DatabaseCleaner(entityManager).clean()
    }

    @Test fun `find customers with credit cards that are about to expire`() {
        val deadLine = "6 Jun 2009"

        addCustomers(
            aCustomer().withName("Alice (Expired)")
                .withPaymentMethods(aCreditCard().withExpiryDate(date("1 Jan 2009"))),
            aCustomer().withName("Bob (Expired)")
                .withPaymentMethods(aCreditCard().withExpiryDate(date("5 Jun 2009"))),
            aCustomer().withName("Carol (Valid)")
                .withPaymentMethods(aCreditCard().withExpiryDate(date(deadLine))),
            aCustomer().withName("Dave (Valid)")
                .withPaymentMethods(aCreditCard().withExpiryDate(date("7 Jun 2009"))),
        )

        assertCustomersExpiresOn(date(deadLine), List<Customer>::isNotEmpty)
        assertCustomersExpiresOn(date(deadLine), contains(customerWithName("Alice (Expired)"), customerWithName("Bob (Expired)")))
    }


    private fun date(date: String): LocalDate {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("d MMM yyyy"))
    }

    private fun customerWithName(name: String): (Customer) -> Boolean = { it.name == name }

    private fun contains(vararg predicates: (Customer) -> Boolean): (List<Customer>) -> Boolean {
        return { customers ->
            predicates.map { customers.any(it) }.all { it }
        }
    }

    private fun addCustomers(vararg customers: CustomerBuilder) {
        transactor.perform(object : UnitOfWork {
            override fun work() {
                customers.forEach {
                    customerBase.addCustomer(it.build())
                }
            }
        })
    }

    private fun assertCustomersExpiresOn(date: LocalDate, predicate: (List<Customer>) -> Boolean) {
        transactor.perform(object : UnitOfWork {
            override fun work() {
                assertThat(customerBase.customersWithExpiredCreditCardsAsOf(date)).matches(predicate)
            }
        })
    }

    @After fun tearDown() {
        entityManager.close()
        factory.close()
    }
}
