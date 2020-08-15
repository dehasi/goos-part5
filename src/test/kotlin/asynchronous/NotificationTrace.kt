package asynchronous

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
    }
}
