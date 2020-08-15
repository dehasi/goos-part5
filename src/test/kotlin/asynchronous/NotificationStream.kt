package asynchronous

class NotificationStream<NOTIFICATION>(val trace: MutableList<NOTIFICATION>, val criteria: (NOTIFICATION) -> Boolean) {

    fun hasMatched() = trace.any(criteria)

    fun hasNotMatched() = !hasMatched()
}
