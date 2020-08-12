package persistence.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType.JOINED

@Entity
@Inheritance(strategy = JOINED)
//@DiscriminatorColumn(name = "type", discriminatorType = STRING)
abstract class PaymentMethod(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    open val id: Int)

