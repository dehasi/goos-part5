package persistence

import javax.persistence.EntityManager
import javax.persistence.PersistenceException

class JPATransactor(private val entityManager: EntityManager) {

    fun perform(unitOfWork: UnitOfWork) {
        val transaction = entityManager.transaction
        transaction.begin()
        try {
            unitOfWork.work()
            transaction.commit()
        } catch (e: PersistenceException) {
            throw e
        } catch (e: Exception) {
            transaction.rollback()
            throw e
        }
    }
}
