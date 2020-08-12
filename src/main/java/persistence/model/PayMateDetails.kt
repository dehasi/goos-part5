package persistence.model

import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

@Entity
data class PayMateDetails(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    override val id: Int,
    @Embedded val auth: Authorisation) : PaymentMethod(id)
