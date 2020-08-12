package persistence.builders

abstract class AbstractBuilder<TYPE : AbstractBuilder<TYPE, BUILDER>, BUILDER> : Cloneable, Builder<BUILDER> {

    override fun clone(): TYPE {
        return try {
            @Suppress("UNCHECKED_CAST")
            super.clone() as TYPE
        } catch (e: CloneNotSupportedException) {
            throw Error(e)
        }
    }
}
