package persistence

import jpa.PersistentCustomerBase
import javax.persistence.Persistence

class PersistentCustomerBaseTest {

    private var entityManager = Persistence.createEntityManagerFactory("GOOS")
        .createEntityManager()
    private val customerBase = PersistentCustomerBase(entityManager)
}
