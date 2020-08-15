package asynchronous.polling

interface Probe {

    fun sample()
    fun isSatisfied(): Boolean
    fun isNotSatisfied() = !isSatisfied()
    fun desireFailure(): String
}
