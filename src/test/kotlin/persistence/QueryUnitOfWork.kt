package persistence

abstract class QueryUnitOfWork<T> : UnitOfWork {

    var result: T? = null

    abstract fun query(): T

    override fun work() {
        result = query()
    }
}
