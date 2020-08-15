package asynchronous

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.lang.System.currentTimeMillis
import java.util.concurrent.TimeUnit.MILLISECONDS

class TimeoutTest {

    @Test fun `reports if timed out`() {
        val timeout = Timeout(100)
        assertThat(timeout.hasTimedOut()).`as`("should not have timed out").isFalse()
        Thread.sleep(100)
        assertThat(timeout.hasTimedOut()).`as`("should have timed out").isTrue()
    }

    @Test @org.junit.jupiter.api.Timeout(value = 300, unit = MILLISECONDS) fun `waits for timeout`() {
        val lock = Object()
        val start = currentTimeMillis()
        val timeout = Timeout(250)

        synchronized(lock) {
            timeout.waitOn(lock)
        }

        val woken = currentTimeMillis()
        assertThat(woken - start).`as`("should have waited until the timeout").isGreaterThanOrEqualTo(250)
    }
}
