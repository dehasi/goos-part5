package asynchronous.notification

import asynchronous.Timeout

class NotificationTrace<NOTIFICATION>(private val timeOutMs: Long) {

    private val traceLock = Object()
    private val trace = mutableListOf<NOTIFICATION>()

    fun append(message: NOTIFICATION) {
        synchronized(traceLock) {
            trace.add(message)
            traceLock.notifyAll()
        }
    }

    fun containsNotification(criteria: (NOTIFICATION) -> Boolean) {
        val timeout = Timeout(timeOutMs)

        synchronized(traceLock) {
            val stream = NotificationStream(trace, criteria)

            while (stream.hasNotMatched()) {
                if (timeout.hasTimedOut()) {
                    throw AssertionError(failreDeceptionFrom(criteria))
                }
                timeout.waitOn(traceLock)
            }
        }
    }

    private fun failreDeceptionFrom(criteria: (NOTIFICATION) -> Boolean) = criteria.toString()
}
