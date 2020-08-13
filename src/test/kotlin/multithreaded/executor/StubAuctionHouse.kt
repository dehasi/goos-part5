package multithreaded.executor

import multithreaded.searching.Auction
import multithreaded.searching.AuctionDescription
import multithreaded.searching.AuctionHouse
import java.util.*


class StubAuctionHouse : AuctionHouse {
    private val searchResults: MutableMap<Set<String>, List<AuctionDescription>> = HashMap()
    private val name: String

    constructor() {
        name = javaClass.simpleName
    }

    constructor(name: String) {
        this.name = name
    }

    override fun toString(): String {
        return name
    }

    fun willReturnSearchResults(keywords: Set<String>, results: List<AuctionDescription>) {
        searchResults[keywords] = results
    }

    override fun findAuctions(keywords: Iterable<String>): List<AuctionDescription> {
        return searchResults.getOrDefault(keywords, emptyList())
    }

    override fun joinAuction(auctionId: String): Auction {
        TODO("not implemented")
    }
}
