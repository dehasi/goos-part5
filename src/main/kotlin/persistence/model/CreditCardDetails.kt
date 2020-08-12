package persistence.model

import java.time.LocalDate
import javax.persistence.CascadeType.PERSIST
import javax.persistence.Entity
import javax.persistence.FetchType.EAGER
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
data class CreditCardDetails(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    override val id: Int,
    val cardNumber: String,
    val nameOnCard: String,
    val expiryDate: LocalDate,

    @ManyToOne(cascade = [PERSIST], fetch = EAGER, optional = false)
    val billingAddress: Address
) : PaymentMethod(id)
