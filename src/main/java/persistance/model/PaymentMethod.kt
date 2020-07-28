package persistance.model

import javax.persistence.*
import javax.persistence.DiscriminatorType.STRING
import javax.persistence.GenerationType.AUTO
import javax.persistence.InheritanceType.JOINED

@Entity
@Inheritance(strategy = JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = STRING)
open class PaymentMethod(
        @Id
        @GeneratedValue(strategy = AUTO)
        val id: Int)

