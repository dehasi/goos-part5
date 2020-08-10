package persistence.builders

import persistence.model.Authorisation


class AuthorisationBuilder : AbstractBuilder<AuthorisationBuilder, Authorisation>() {

    private var userName = "user"
    private var password = "pass"

    fun withUserName(userName: String): AuthorisationBuilder? {
        val other = clone()
        other.userName = userName
        return other
    }

    fun withPassword(password: String): AuthorisationBuilder? {
        val other = clone()
        other.password = password
        return other
    }

    override fun build(): Authorisation {
        return Authorisation(userName, password)
    }
}
