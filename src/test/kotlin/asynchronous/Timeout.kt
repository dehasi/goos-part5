package asynchronous

import java.lang.System.currentTimeMillis

class Timeout(durationMillis: Long) {

    private val endTime = currentTimeMillis() + durationMillis

    fun hasTimedOut() = timeRemaining() < 0

    fun waitOn(lock: Object) {
        val waitTime = timeRemaining()
        if (waitTime > 0) lock.wait(waitTime)
    }

    private fun timeRemaining() = endTime - currentTimeMillis()
}
