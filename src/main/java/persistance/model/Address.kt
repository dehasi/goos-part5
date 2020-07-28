package persistance.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.AUTO
import javax.persistence.Id


@Entity
data class Address(
        @Id
        @GeneratedValue(strategy = AUTO)
        val id: Int,
        val street: String,
        val town: String,
        val country: String,
        val postCode: String
)
