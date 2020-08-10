package persistence.builders

import persistence.model.AuctionSite

import persistence.model.AuctionSiteCredentials

import persistence.model.Authorisation


class AuctionSiteLoginBuilder : AbstractBuilder<AuctionSiteLoginBuilder, AuctionSiteCredentials>() {

    private var auctionSiteBuilder: Builder<AuctionSite> = AuctionSiteBuilder()
    private var authorisationBuilder: Builder<Authorisation> = AuthorisationBuilder()

    fun forSite(auctionSiteBuilder: Builder<AuctionSite>): AuctionSiteLoginBuilder? {
        val other = clone()
        other.auctionSiteBuilder = auctionSiteBuilder
        return other
    }

    fun with(authorisationBuilder: AuthorisationBuilder): AuctionSiteLoginBuilder? {
        val other = clone()
        other.authorisationBuilder = authorisationBuilder
        return other
    }

    override fun build(): AuctionSiteCredentials {
        return AuctionSiteCredentials(0, auctionSiteBuilder.build(), authorisationBuilder.build())
    }
}
