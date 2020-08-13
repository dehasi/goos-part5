package multithreaded.searching

interface AuctionSearchConsumer {

    fun auctionSearchFound(auctions: List<AuctionDescription>)

    fun auctionSearchFinished()
}
