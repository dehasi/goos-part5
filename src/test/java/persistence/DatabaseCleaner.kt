package persistence

import persistence.model.Address
import persistence.model.AuctionSite
import persistence.model.AuctionSiteCredentials
import persistence.model.Customer
import persistence.model.PaymentMethod
import javax.persistence.EntityManager


class DatabaseCleaner(private val entityManager: EntityManager) {

    fun clean() {
        val transaction = entityManager.transaction
        transaction.begin()
        for (entityType in ENTITY_TYPES) {
            deleteEntities(entityType)
        }
        transaction.commit()
    }

    private fun deleteEntities(entityType: Class<*>) {
        entityManager
            .createQuery("delete from " + entityNameOf(entityType))
            .executeUpdate()
    }

    private fun entityNameOf(entityType: Class<*>): String {
        return entityType.simpleName
    }

    companion object {
        private val ENTITY_TYPES = arrayOf(
            Address::class.java,
            AuctionSite::class.java,
            AuctionSiteCredentials::class.java,
            Customer::class.java,
            PaymentMethod::class.java
        )
    }
}
