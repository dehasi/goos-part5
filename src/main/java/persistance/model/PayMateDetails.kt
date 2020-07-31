package persistance.model

import javax.persistence.Embedded
import javax.persistence.Entity

// todo add a proper inheritance
@Entity
data class PayMateDetails(override val id: Int, @Embedded val auth: Authorisation) : PaymentMethod(id)
