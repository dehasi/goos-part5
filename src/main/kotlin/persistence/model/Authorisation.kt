package persistence.model

import javax.persistence.Embeddable

@Embeddable
data class Authorisation(
    val userName: String,
    val password: String)
