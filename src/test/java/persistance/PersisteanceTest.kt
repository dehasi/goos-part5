package persistance

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import persistance.model.Address
import javax.persistence.Persistence


class PersisteanceTest {
    var em = Persistence.createEntityManagerFactory("GOOS")
        .createEntityManager()

    @Test fun simple_test() {
        val  address = Address(23,"","","","")

        em.persist(address);

        val fronDB = em.find(Address::class.java, 23)

        assertThat(fronDB).isEqualTo(address)
    }
}
