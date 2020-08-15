package maybe

import java.util.function.Function
import java.util.function.Predicate

abstract class Maybe<T> : Iterable<T> {
    abstract fun isKnown(): Boolean
    abstract fun otherwise(defaultValue: T): T
    abstract fun otherwise(maybeDefaultValue: Maybe<T>): Maybe<T>
    abstract fun <U> to(mapping: Function<in T, out U>): Maybe<U>
    abstract fun query(mapping: Predicate<in T>): Maybe<Boolean>

    companion object {
        fun <T> unknown(): Maybe<T> {
            return object : Maybe<T>() {
                override fun isKnown() = false

                override fun otherwise(defaultValue: T) = defaultValue
                override fun otherwise(maybeDefaultValue: Maybe<T>) = maybeDefaultValue

                override fun <U> to(mapping: Function<in T, out U>): Maybe<U> = unknown()

                override fun query(mapping: Predicate<in T>): Maybe<Boolean> = unknown()

                override fun iterator() = listOf<T>().iterator()

                override fun toString() = "unknown"
                override fun equals(other: Any?) = false
                override fun hashCode() = 0
            }
        }

        fun <T> definitely(value: T): Maybe<T> {
            return object : Maybe<T>() {
                override fun isKnown() = true
                override fun otherwise(defaultValue: T) = value

                override fun otherwise(maybeDefaultValue: Maybe<T>) = this

                override fun <U> to(mapping: Function<in T, out U>) = definitely(mapping.apply(value))

                override fun query(mapping: Predicate<in T>) = definitely(mapping.test(value))

                override fun iterator() = listOf<T>(value).iterator()

                override fun toString() = "definitely $value"
                override fun equals(other: Any?) = other == value
                override fun hashCode() = value.hashCode()
            }
        }
    }
}


