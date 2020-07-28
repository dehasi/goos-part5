package persistance.model

import javax.persistence.DiscriminatorColumn
import javax.persistence.DiscriminatorType.STRING
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.AUTO
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType.JOINED

@Entity
@Inheritance(strategy = JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = STRING)
open class PaymentMethod(
    @Id
    @GeneratedValue(strategy = AUTO)
    val id: Int)

