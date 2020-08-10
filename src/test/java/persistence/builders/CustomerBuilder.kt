package persistence.builders

import persistence.model.Address
import persistence.model.AuctionSiteCredentials
import persistence.model.Customer
import persistence.model.PaymentMethod
import java.util.Collections.emptySet


class CustomerBuilder : AbstractBuilder<CustomerBuilder, Customer>() {

    private var name = "customer"
    private var email = "customer@example.com"
    private var addressBuilder: Builder<Address> = AddressBuilder()
    private var paymentMethods: Set<Builder<out PaymentMethod>> = emptySet()
    private var auctionSitesUsed: Set<Builder<out AuctionSiteCredentials>> = emptySet()

    fun withName(name: String): CustomerBuilder {
        val other = clone()
        other.name = name
        return other
    }

    fun withEmailAddress(email: String): CustomerBuilder {
        val other = clone()
        other.email = email
        return other
    }

    fun withAddress(addressBuilder: Builder<Address>): CustomerBuilder {
        val other = clone()
        other.addressBuilder = addressBuilder
        return other
    }

    fun withPaymentMethods(vararg paymentMethodBuilders: Builder<out PaymentMethod>): CustomerBuilder {
        val other = clone()
        other.paymentMethods = setOf(*paymentMethodBuilders)
        return other
    }

    fun usingAuctionSites(vararg auctionSiteLoginBuilders: Builder<AuctionSiteCredentials>): CustomerBuilder {
        val other = clone()
        other.auctionSitesUsed = setOf(*auctionSiteLoginBuilders)
        return other
    }

    private fun <T> setOf(vararg builders: Builder<out T>): Set<Builder<out T>> {
        return mutableSetOf(*builders)
    }

    override fun build(): Customer {
        return Customer(0, name, email, addressBuilder.build(), buildSet(paymentMethods), buildSet(auctionSitesUsed))
    }

    companion object {
        fun aCustomer(): CustomerBuilder {
            return CustomerBuilder()
        }

        private fun <T> buildSet(builders: Set<Builder<out T>>): Set<T> {
            val set = HashSet<T>()
            for (builder in builders) {
                set.add(builder.build())
            }
            return set
        }
    }
}
