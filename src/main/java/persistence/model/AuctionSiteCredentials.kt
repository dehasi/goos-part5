package persistence.model

import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class AuctionSiteCredentials(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Int,
    @ManyToOne
    val site: AuctionSite,
    @Embedded
    val auth: Authorisation
)
