package maybe

import java.util.function.Function
import java.util.function.Predicate

abstract class Maybe<T> : Iterable<T> {
    abstract fun isKnown(): Boolean
    abstract fun otherwise(defaultValue: T): T
    abstract fun otherwise(maybeDefaultValue: Maybe<T>): Maybe<T>
    abstract fun <U> to(mapping: Function<in T, out U>): Maybe<U>
    abstract fun query(mapping: Predicate<in T>): Maybe<Boolean>
}


