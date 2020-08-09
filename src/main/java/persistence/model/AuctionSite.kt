package persistence.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.AUTO
import javax.persistence.Id

@Entity
data class AuctionSite(
    @Id
    @GeneratedValue(strategy = AUTO)
    val id: Int,
    val name: String,
    val siteURL: String
)
