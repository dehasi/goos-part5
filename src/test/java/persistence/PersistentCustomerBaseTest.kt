package persistence

import jpa.PersistentCustomerBase
import org.junit.jupiter.api.Test
import persistence.builders.CustomerBuilder
import javax.persistence.Persistence

class PersistentCustomerBaseTest {

    private var entityManager = Persistence.createEntityManagerFactory("GOOS")
        .createEntityManager()
    private val customerBase = PersistentCustomerBase(entityManager)

    @Test fun `find customers with credit cards that are about to expire`() {
        val deadLine = "6 Jun 2009"

    }

    private fun addCustomers(customers : Array<CustomerBuilder>){

    }
}
