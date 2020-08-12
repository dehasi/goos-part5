package multithreaded

interface AuctionSearchConsumer {

    fun auctionSearchFound(auctions: List<AuctionDescription>)
    fun auctionSearchFinished()
}
