package persistence.model

import javax.persistence.Embedded
import javax.persistence.Entity

@Entity
data class PayMateDetails(override val id: Int, @Embedded val auth: Authorisation) : PaymentMethod(id)
