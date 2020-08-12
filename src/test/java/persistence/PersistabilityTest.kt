package persistence

import org.junit.After
import org.junit.Before
import persistence.builders.AddressBuilder
import persistence.builders.AuctionSiteBuilder
import persistence.builders.AuctionSiteLoginBuilder
import persistence.builders.Builder
import persistence.builders.CreditCardDetailsBuilder
import persistence.builders.CustomerBuilder
import persistence.builders.PayMateDetailsBuilder
import persistence.model.AuctionSite
import javax.persistence.Persistence


class PersistabilityTest {

    private val factory = Persistence.createEntityManagerFactory("GOOS")
    private var entityManager = factory.createEntityManager()
    private val transactor = JPATransactor(entityManager)

    val persistentObjectBuilders: List<Builder<*>> = listOf(
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


    private fun persisted(auctionSiteBuilder: AuctionSiteBuilder): Builder<AuctionSite> {
        TODO("Not yet implemented")
    }

    @Before fun cleanDatabase() {
        DatabaseCleaner(entityManager).clean()
    }


    @After fun tearDown() {
        entityManager.close()
        factory.close()
    }
}
