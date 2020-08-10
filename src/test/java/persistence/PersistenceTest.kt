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
        val address = Address(0, "Hogeweg 106", "Amsterdam", "Netherlands", "1034HF")
        entityManager.persist(address);

        val fromDB = entityManager.find(Address::class.java, address.id+1)
    val  a:   List<Address>  = entityManager.createQuery("SELECT a FROM Address a").getResultList() as List<Address>;

        assertThat(fromDB).isEqualTo(address)
    }
}
