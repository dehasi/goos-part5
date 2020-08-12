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
import java.lang.reflect.Field
import javax.persistence.Id
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
                    .`as`("for entity ${original.javaClass.simpleName}")
                    .isEqualToComparingFieldByField(original)
            }
        })
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

    @After fun tearDown() {
        entityManager.close()
        factory.close()
    }

    private companion object {

        fun idOf(entity: Any): Any? {
            var c: Class<*> = entity.javaClass
            while (c != Any::class.java) {
                for (field in c.declaredFields) {
                    if (field.isAnnotationPresent(Id::class.java)) {
                        return fieldValue(entity, field)
                    }
                }
                c = c.superclass
            }
            throw IllegalArgumentException("$entity does not have an entity id")
        }

        private fun fieldValue(entity: Any, field: Field): Any? {
            field.isAccessible = true
            return try {
                field.get(entity)
            } catch (e: IllegalAccessException) {
                throw Error("could not access accessible field $field")
            }
        }

        private fun typeNameFor(builder: Builder<*>) =
            builder.javaClass.simpleName.replace("Builder", "")

    }
}
