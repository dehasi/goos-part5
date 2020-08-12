package multithreaded

import io.mockk.mockk
import org.junit.jupiter.api.Test

class AuctionSearchTest {
    private val executor = DeterministicExecutor()
    private val houseA = StubAuctionHouse("houseA")
    private val houseB = StubAuctionHouse("houseB")

    private val resultsFromA = listOf(auction(houseA, "1"))
    private val resultsFromB = listOf(auction(houseB, "2"))

    private val consumer: AuctionSearchConsumer = mockk()
    private val search = AuctionSearch(executor, houses(houseA, houseB), consumer)

    @Test fun `searches all auction houses`() {
        val keywords = setOf("sheep", "cheese")

        houseA.willReturnSearchResults(keywords, resultsFromA)
        houseB.willReturnSearchResults(keywords, resultsFromB)

        search.search(keywords)
        executor.runUntilIdle()
    }


    private fun houses(vararg houses: AuctionHouse) = listOf(*houses)

    private fun auction(house: AuctionHouse, id: String) =
        AuctionDescription(house, id, "test auction")
}
