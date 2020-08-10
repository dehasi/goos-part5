package persistence

import jpa.PersistentCustomerBase
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Test
import persistence.builders.CustomerBuilder
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

    }

    private fun addCustomers(customers: Array<CustomerBuilder>) {
        transactor.perform(object : UnitOfWork {
            override fun work() {
                customers.forEach {
                    customerBase.addCustomer(it.build())
                }
            }
        })
    }

    @After fun tearDown() {
        entityManager.close()
        factory.close()
    }
}
