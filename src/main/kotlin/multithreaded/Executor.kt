package multithreaded

interface Executor {
    fun execute(command: Runnable)
}
