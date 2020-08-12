package multithreaded

interface AuctionHouse {

    fun findAuctions(keywords: Set<String>): List<AuctionDescription>

    fun joinAuction(auctionId: String): Auction
}
