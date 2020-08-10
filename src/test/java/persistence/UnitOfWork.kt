package persistence

@FunctionalInterface
interface UnitOfWork {
    fun work()
}
