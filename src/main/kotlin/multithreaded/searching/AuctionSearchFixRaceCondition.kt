package multithreaded.searching

import java.util.concurrent.Executor
import java.util.concurrent.atomic.AtomicInteger

class AuctionSearchFixRaceCondition(
    private val executor: Executor,
    private val auctionHouses: Collection<AuctionHouse>,
    private val consumer: AuctionSearchConsumer) {

    private var runningSearchCount = AtomicInteger()

    fun search(keywords: Set<String>) {
        runningSearchCount.set(auctionHouses.size)
        auctionHouses.forEach {
            startSearching(it, keywords)
        }
    }

    private fun startSearching(auctionHouse: AuctionHouse, keywords: Iterable<String>) {
        // no more increments thr count here
        executor.execute { search(auctionHouse, keywords) }
    }

    private fun search(auctionHouse: AuctionHouse, keywords: Iterable<String>) {
        consumer.auctionSearchFound(auctionHouse.findAuctions(keywords))

        if (runningSearchCount.decrementAndGet() == 0) {
            consumer.auctionSearchFinished()
        }
    }
}
