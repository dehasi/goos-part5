package persistence.builders

import persistence.model.CreditCardDetails
import java.time.LocalDate


class CreditCardDetailsBuilder : AbstractBuilder<CreditCardDetailsBuilder, CreditCardDetails>() {

    private var nameOnCard = "bob"
    private var cardNumber = "1234567890"
    private var expiryDate: LocalDate = LocalDate.now()
    private var billingAddressBuilder = AddressBuilder()

    fun withNameOnCard(nameOnCard: String): CreditCardDetailsBuilder {
        val other = clone()
        other.nameOnCard = nameOnCard
        return other
    }

    fun withCardNumber(cardNumber: String): CreditCardDetailsBuilder {
        val other = clone()
        other.cardNumber = cardNumber
        return other
    }

    fun withExpiryDate(expiryDate: LocalDate): CreditCardDetailsBuilder {
        val other = clone()
        other.expiryDate = expiryDate
        return other
    }

    fun withBillingAddress(billingAddressBuilder: AddressBuilder): CreditCardDetailsBuilder {
        val other = clone()
        other.billingAddressBuilder = billingAddressBuilder
        return other
    }

    override fun build(): CreditCardDetails {
        return CreditCardDetails(0, nameOnCard, cardNumber, expiryDate, billingAddressBuilder.build())
    }

    companion object {
        fun aCreditCard(): CreditCardDetailsBuilder {
            return CreditCardDetailsBuilder()
        }
    }
}
