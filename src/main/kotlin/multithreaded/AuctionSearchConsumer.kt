package multithreaded

class AuctionSearchConsumer(
    private val executor: Executor,
    private val houses: Collection<AuctionHouse>,
    private val consumer: AuctionSearchConsumer) {

    fun auctionSearchFound(findAuctions: Any) {
        TODO("Not yet implemented")
    }

    fun auctionSearchFinished() {
        TODO("Not yet implemented")
    }
}
