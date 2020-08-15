package asynchronous.polling

import org.junit.jupiter.api.Test
import java.io.File

class FileLengthProbeTest {

    @Test fun `check gradle file`() {
        assertEventually("build.gradle") { it > 1000 }
    }

    private fun assertEventually(path: String, predicate: (Long) -> Boolean) {
        val probe = fileLength(path, predicate)
        Poller(1000, 1000).check(probe)
    }

    private fun fileLength(path: String, predicate: (Long) -> Boolean): Probe {
        val file = File(path)

        return object : Probe {
            private val NOT_SET = -1L
            private var lastFileLength = NOT_SET
            override fun sample() {
                lastFileLength = file.length()
            }

            override fun isSatisfied(): Boolean {
                return lastFileLength != NOT_SET && predicate.invoke(lastFileLength)
            }

            override fun desireFailure() = "File length was $lastFileLength"
        }
    }
}
