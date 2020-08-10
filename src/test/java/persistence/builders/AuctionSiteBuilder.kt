package persistence.builders

import persistence.model.AuctionSite


class AuctionSiteBuilder : AbstractBuilder<AuctionSiteBuilder, AuctionSite>() {

    private var name = "eCove"
    private var siteURL = "http://www.ecove.com"

    fun withName(aName: String): AuctionSiteBuilder? {
        val other = clone()
        other.name = aName
        return other
    }

    fun withSiteURL(aSiteURL: String): AuctionSiteBuilder? {
        val other = clone()
        other.siteURL = aSiteURL
        return other
    }

    override fun build(): AuctionSite {
        return AuctionSite(0, name, siteURL)
    }

    companion object {
        fun anAuctionSite(): AuctionSiteBuilder {
            return AuctionSiteBuilder()
        }
    }
}
