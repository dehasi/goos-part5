package persistence.builders

interface Builder<TYPE> {
    fun build(): TYPE
}
