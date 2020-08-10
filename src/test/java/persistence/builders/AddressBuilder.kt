package persistence.builders

import persistence.model.Address

class AddressBuilder : AbstractBuilder<AddressBuilder, Address>() {

    private var street = "1 High Street"
    private var town = "Bognor Regis"
    private var country = "UK"
    private var postCode = "BG1 2FO"

    fun withStreet(street: String): AddressBuilder {
        val other: AddressBuilder = clone()
        other.street = street
        return other
    }

    fun withTown(town: String): AddressBuilder {
        val other: AddressBuilder = clone()
        other.town = town
        return other
    }

    fun withCountry(country: String): AddressBuilder {
        val other: AddressBuilder = clone()
        other.country = country
        return other
    }

    fun withPostCode(postCode: String): AddressBuilder {
        val other: AddressBuilder = clone()
        other.postCode = postCode
        return other
    }

    override fun build(): Address {
        return Address(0, street, town, country, postCode)
    }
}
