package persistence

import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Test
import persistence.builders.AddressBuilder
import persistence.builders.AuctionSiteBuilder
import persistence.builders.AuctionSiteLoginBuilder
import persistence.builders.Builder
import persistence.builders.CreditCardDetailsBuilder
import persistence.builders.CustomerBuilder
import persistence.builders.PayMateDetailsBuilder
import javax.persistence.Persistence
import javax.persistence.PersistenceException


class PersistabilityTest {

    private val factory = Persistence.createEntityManagerFactory("GOOS")
    private var entityManager = factory.createEntityManager()
    private val transactor = JPATransactor(entityManager)

    private val persistentObjectBuilders: List<Builder<*>> = listOf(
        AddressBuilder(),
        PayMateDetailsBuilder(),
        CreditCardDetailsBuilder(),
        AuctionSiteBuilder(),
        AuctionSiteLoginBuilder().forSite(persisted(AuctionSiteBuilder())),
        CustomerBuilder()
            .usingAuctionSites(
                AuctionSiteLoginBuilder().forSite(persisted(AuctionSiteBuilder())))
            .withPaymentMethods(
                CreditCardDetailsBuilder(),
                PayMateDetailsBuilder()))


    @Before fun cleanDatabase() {
        DatabaseCleaner(entityManager).clean()
    }

    @Test fun `round trip persistent objects`() {
        persistentObjectBuilders.forEach {
            assertCanBePersisted(it);
        }
    }

    private fun assertCanBePersisted(builder: Builder<*>) {
        try {
            assertReloadWithSameStateAs(persistedObjectFrom(builder))
        } catch (e: PersistenceException) {
            throw PersistenceException("Could not round-trip " + typeNameFor(builder), e)
        }
    }

    private fun persistedObjectFrom(builder: Builder<*>): Any {
        return transactor.performQuery(object : QueryUnitOfWork<Any>() {
            override fun query(): Any {
                // Build the saved object in the transaction so any sub-builders can do database activity if necessary
                val saved = builder.build()!!
                entityManager.persist(saved)
                return saved
            }
        })
    }

    private fun assertReloadWithSameStateAs(original: Any) {
        transactor.perform(object : UnitOfWork {
            override fun work() {
                assertThat(entityManager.find(original.javaClass, idOf(original)))
                    .isEqualToComparingFieldByField(original)
            }
        })
    }

    private fun idOf(original: Any): Any? {
        TODO("Not yet implemented")
    }

    private fun <TYPE> persisted(builder: Builder<TYPE>): Builder<TYPE> {
        return object : Builder<TYPE> {
            override fun build(): TYPE {
                val entity = builder.build()
                entityManager.persist(entity);
                return entity
            }
        }
    }

    private fun typeNameFor(builder: Builder<*>) =
        builder.javaClass.simpleName.replace("Builder", "")


    @After fun tearDown() {
        entityManager.close()
        factory.close()
    }
}
