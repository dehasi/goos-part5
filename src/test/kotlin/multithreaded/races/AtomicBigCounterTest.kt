package multithreaded.races

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class AtomicBigCounterTest {

    private val counter = AtomicBigCounter()

    @Disabled("to make build pass. The test should fail")
    @Test fun `can increment counter from multiple threads simultaneously`() {
        val stressTester = MultithreadedStressTester(50_000)

        stressTester.stress(Runnable {
            counter.inc()
        })
        stressTester.shutdown()

        assertThat(counter.count()).`as`("final count").isEqualTo(stressTester.totalActionCount())
    }
}
