package persistence.model

import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.AUTO
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class AuctionSiteCredentials(
    @Id
    @GeneratedValue(strategy = AUTO)
    val id: Int,
    @ManyToOne
    val site: AuctionSite,
    @Embedded
    val auth: Authorisation
)
