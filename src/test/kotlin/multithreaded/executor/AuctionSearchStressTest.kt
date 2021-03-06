package multithreaded.executor

import io.mockk.mockk
import io.mockk.verify
import multithreaded.searching.AuctionDescription
import multithreaded.searching.AuctionHouse
import multithreaded.searching.AuctionSearchConsumer
import multithreaded.searching.AuctionSearchFixRaceCondition
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit.SECONDS

class AuctionSearchStressTest {

    private val numberOfAuctionHouses = 4
    private val numberOfSearches = 8
    private val keywords = setOf("sheep", "cheese")

    private val consumer: AuctionSearchConsumer = mockk(relaxUnitFun = true)

    private val executor = Executors.newCachedThreadPool()

    // private val search = AuctionSearch(executor, auctionHouses(), consumer)
    // private val search = AuctionSearchWithAtomicInteger(executor, auctionHouses(), consumer)
    private val search = AuctionSearchFixRaceCondition(executor, auctionHouses(), consumer)

    private fun auctionHouses(): Collection<AuctionHouse> {
        val auctionHouses = mutableListOf<AuctionHouse>()
        for (i in 0 until numberOfAuctionHouses) auctionHouses.add(stubbedAuctionHouse(i))
        return auctionHouses
    }

    private fun stubbedAuctionHouse(id: Int): AuctionHouse {
        val house = StubAuctionHouse("house $id")
        house.willReturnSearchResults(
            keywords, listOf(AuctionDescription(house, "id $id", "description")))
        return house
    }

    @Test fun `only one auction search finished not notification present`() {
        for (i in 0 until numberOfSearches) completeASearch()
    }

    private fun completeASearch() {
        search.search(keywords)

        verify {
            consumer.auctionSearchFinished()
        }
    }

    @AfterEach fun `shut down executor`() {
        executor.shutdown()
        executor.awaitTermination(1, SECONDS)
    }
}
