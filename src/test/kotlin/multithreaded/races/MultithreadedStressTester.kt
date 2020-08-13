package multithreaded.races

import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors.newCachedThreadPool
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


class MultithreadedStressTester(
    private val iterationCount: Int,
    private val threadCount: Int = 2,
    private val executor: ExecutorService = newCachedThreadPool()) {

    fun totalActionCount() = threadCount * iterationCount

    fun stress(action: Runnable) {
        spawnThreads(action).await()
    }

    fun blitz(timeoutMs: Long, action: Runnable) {
        if (!spawnThreads(action).await(timeoutMs, TimeUnit.MILLISECONDS)) {
            throw TimeoutException("timed out waiting for blitzed actions to complete successfully")
        }
    }

    private fun spawnThreads(action: Runnable): CountDownLatch {
        val finished = CountDownLatch(threadCount)

        for (i in 0 until threadCount) {
            executor.execute {
                try {
                    repeatAction(action)
                } finally {
                    finished.countDown()
                }
            }
        }
        return finished
    }

    private fun repeatAction(action: Runnable) {
        for (i in 0 until iterationCount) action.run()
    }

    fun shutdown() = executor.shutdown()
}
