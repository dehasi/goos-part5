package maybe

import maybe.Maybe.Companion.definitely
import maybe.Maybe.Companion.unknown
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.function.Function
import java.util.function.Predicate

class MaybeTest {
    class Customer {
        private var emailAddress: Maybe<String>

        constructor(emailAddress: String) {
            this.emailAddress = definitely(emailAddress)
        }

        constructor() {
            this.emailAddress = unknown()
        }

        fun emailAddress(): Maybe<String> {
            return emailAddress
        }
    }

    @Test fun `equals of known values`() {
        assertThat(definitely(1)).isEqualTo(definitely(1))
        assertThat(definitely(1)).isNotEqualTo(definitely(2))
    }

    @Test fun `unknown values are never equal`() {
        assertThat(unknown<String>()).isNotEqualTo(unknown<String>())
        val u: Maybe<Any> = unknown()
        assertThat(u).isNotEqualTo(u)
    }

    @Test fun `an unknown thing is never equal to aKnown thing`() {
        assertThat(unknown<Int>()).isNotEqualTo(definitely(1))
        assertThat(unknown<String>()).isNotEqualTo(definitely("rumsfeld"))
        assertThat(definitely(1)).isNotEqualTo(unknown<Int>())

        assertThat(definitely("rumsfeld")).isNotEqualTo(unknown<String>())
    }

    @Test fun `otherwise a default value`() {
        assertThat(noString().otherwise("")).isEqualTo("")
        assertThat(definitely("foo").otherwise("")).isEqualTo("foo")
    }

    @Test fun `chaining otherwise`() {
        assertThat(noString().otherwise(noString()).otherwise("")).isEqualTo("")
        assertThat(noString().otherwise(definitely("X")).otherwise("")).isEqualTo("X")
        assertThat(definitely("X").otherwise(definitely("Y")).otherwise("")).isEqualTo("X")
    }

    @Test
    fun transforming() {
        assertThat(Customer("alice@example.com").emailAddress().to<Any>(toUpperCase).otherwise("nobody@example.com"))
           .isEqualTo("ALICE@EXAMPLE.COM")
        assertThat(Customer().emailAddress().to<Any>(toUpperCase).otherwise("UNKNOWN"))
            .isEqualTo("UNKNOWN")
    }

    @Test fun querying() {
        assertThat(definitely("example@example.com").query(isValidEmailAddress)).isEqualTo(definitely(true))
        assertThat(definitely("invalid-email-address").query(isValidEmailAddress)).isEqualTo(definitely(false))
        assertThat(unknown <String>().query(isValidEmailAddress).isKnown()).isFalse()
    }

    @Test fun ifThen() {
        val foo: Maybe<String> = definitely("foo")
        if (foo.isKnown()) for (s in foo) {
//            assertThat(s, equalTo("foo"))
        } else {
//            fail("should not have been called")
        }
    }

    @Test fun ifElse() {
        val foo: Maybe<String> = unknown()
        if (foo.isKnown()) for (s in foo) {
//            fail("should not have been called")
        } else {
            // ok!
        }
    }

    @Test fun exampleCollectingValidEmailAddresses() {
        val customers: List<Customer> = listOf(
            Customer(),
            Customer("alice@example.com"),
            Customer("bob@example.com"),
            Customer(),
            Customer("alice@example.com")
        )

        val emailAddresses = customers.map { it.emailAddress() }.filter { it.isKnown() }.map { it.otherwise("") }

        assertThat(emailAddresses).containsExactly(
            "alice@example.com",
            "bob@example.com",
            "alice@example.com")
    }

    private fun noString(): Maybe<String> {
        return unknown()
    }

    companion object {
        val isValidEmailAddress: Predicate<String> = Predicate { input -> input.contains("@") }

        val toUpperCase: Function<String, String> = Function { from -> from.toUpperCase() }
    }
}
