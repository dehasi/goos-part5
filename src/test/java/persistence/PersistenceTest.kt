package persistence

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.jupiter.api.Test
import persistence.model.Address
import javax.persistence.Persistence


class PersistenceTest {

    private var entityManager = Persistence.createEntityManagerFactory("GOOS")
        .createEntityManager()

    @Before fun cleanDatabase() {
        DatabaseCleaner(entityManager).clean()
    }


    @Test fun simple_test() {
        val address = Address(0, "", "", "", "")

        entityManager.persist(address);

        val fromDB = entityManager.find(Address::class.java, address.id)

        assertThat(fromDB).isEqualTo(address)
    }
}
