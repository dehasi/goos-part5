package persistence.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

@Entity
data class AuctionSite(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Int,
    val name: String,
    val siteURL: String
)
