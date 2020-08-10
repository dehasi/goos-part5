package persistence.model

import javax.persistence.DiscriminatorColumn
import javax.persistence.DiscriminatorType.STRING
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType.JOINED

@Entity
@Inheritance(strategy = JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = STRING)
open class PaymentMethod(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    open val id: Int)

