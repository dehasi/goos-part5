package asynchronous.polling

interface Probe {

    fun isSatisfied(): Boolean
    fun isNotSatisfied() = !isSatisfied()
    fun sample()
}
