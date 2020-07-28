package persistance.model

import javax.persistence.Embedded
import javax.persistence.Entity

@Entity
data class PayMateDetails(@Embedded val auth: Authorisation) : PaymentMethod(3)
