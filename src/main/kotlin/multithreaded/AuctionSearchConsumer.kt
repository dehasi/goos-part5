package multithreaded

class AuctionSearchConsumer(
    private val executor: Executor,
    private val houses: Collection<AuctionHouse>,
    private val consumer: AuctionSearchConsumer) {
}
