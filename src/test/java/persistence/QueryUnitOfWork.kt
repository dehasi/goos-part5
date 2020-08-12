package persistence

abstract class QueryUnitOfWork<T> : UnitOfWork {

    private var result: T? = null

    abstract fun query(): T

    override fun work() {
        result = query()
    }
}
