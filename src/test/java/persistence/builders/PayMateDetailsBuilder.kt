package persistence.builders

import persistence.model.PayMateDetails


class PayMateDetailsBuilder : AbstractBuilder<PayMateDetailsBuilder, PayMateDetails>() {

    private var authorisationBuilder = AuthorisationBuilder()

    fun withAuthorisation(authorisationBuilder: AuthorisationBuilder): PayMateDetailsBuilder? {
        val other = clone()
        other.authorisationBuilder = authorisationBuilder
        return other
    }

    override fun build(): PayMateDetails {
        return PayMateDetails(0, authorisationBuilder.build())
    }
}
