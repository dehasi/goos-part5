package asynchronous.polling

import asynchronous.Timeout

class Poller(private val timeoutMills: Long, private val pollDelayMills: Long) {

    fun check(probe: Probe) {
        val timeout = Timeout(timeoutMills)

        while (probe.isNotSatisfied()) {
            if (timeout.hasTimedOut()) {
                throw AssertionError(probe.desireFailure())
            }
            Thread.sleep(pollDelayMills)
            probe.sample()
        }
    }
}
