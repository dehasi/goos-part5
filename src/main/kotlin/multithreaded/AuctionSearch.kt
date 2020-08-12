package multithreaded

class AuctionSearch(
    private val executor: Executor,
    private val auctionHouses: Collection<AuctionHouse>,
    private val consumer: AuctionSearchConsumer) {

    private var runningSearchCount = 0

    fun search(keywords: Iterable<String>) {
        auctionHouses.forEach {
            startSearching(it, keywords)
        }
    }

    private fun startSearching(auctionHouse: AuctionHouse, keywords: Iterable<String>) {
        runningSearchCount++

        executor.execute(Runnable { search(auctionHouse, keywords) })
    }

    private fun search(auctionHouse: AuctionHouse, keywords: Iterable<String>) {
        consumer.auctionSearchFound(auctionHouse.findAuctions(keywords))

        runningSearchCount--
        if(runningSearchCount == 0) {
            consumer.auctionSearchFinished()
        }
    }
}
