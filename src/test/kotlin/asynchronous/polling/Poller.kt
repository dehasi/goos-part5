package asynchronous.polling

import asynchronous.Timeout

class Poller(private val timeoutMills: Long, private val pollDelayMills: Long) {

    fun check(probe: Probe) {
        val timeout = Timeout(timeoutMills)

        while (probe.isNotSatisfied()) {
            if (timeout.hasTimedOut()) {
                throw AssertionError(descireFailureFor(probe))
            }
            Thread.sleep(pollDelayMills)
            probe.sample()
        }
    }

    private fun descireFailureFor(probe: Probe) = probe.toString()
}
