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
        val transaction = entityManager.transaction
        transaction.begin()
        entityManager.persist(address);
        transaction.commit()

        val fromDB = entityManager.find(Address::class.java, address.id)
        val addresses: List<Address> = entityManager.createQuery("SELECT a FROM Address a").getResultList() as List<Address>;

        assertThat(addresses).hasSize(1)
        assertThat(fromDB).isEqualTo(address)
    }
}
