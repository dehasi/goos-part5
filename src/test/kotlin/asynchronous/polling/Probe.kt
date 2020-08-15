package asynchronous.polling

interface Probe {

    fun isSatisfied():Boolean
    fun sample()
}
