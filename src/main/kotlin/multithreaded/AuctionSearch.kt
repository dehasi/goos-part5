package multithreaded

class AuctionSearch(
    private val executor: Executor,
    houses: Collection<AuctionHouse>,
    consumer: AuctionSearchConsumer) {
    fun search(keywords: Iterable<String>) {
        TODO("Not yet implemented")
    }
}
