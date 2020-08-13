package multithreaded.races

import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

class AtomicBigCounter {

    private var count = ZERO

    fun count(): BigInteger = count
    fun inc() {
        count = count.add(ONE)
    }
}
