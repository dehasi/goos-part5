package multithreaded.searching

interface AuctionHouse {

    fun findAuctions(keywords: Iterable<String>): List<AuctionDescription>

    fun joinAuction(auctionId: String): Auction
}
