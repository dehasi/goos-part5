package persistance.model


import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.OneToOne
import javax.persistence.ManyToMany
import javax.persistence.CascadeType.ALL
import javax.persistence.GenerationType.AUTO

@Entity
data class Customer(

    @Id
    @GeneratedValue(strategy = AUTO)
    val id: Int,

    val name: String,
    val email: String,

    @OneToOne(cascade = [ALL])
    val address: Address,

    @ManyToMany(cascade = [ALL])
    val paymentMethods: Set<PaymentMethod>,

    @ManyToMany(cascade = [ALL])
    val auctionSiteLogins: Set<AuctionSiteCredentials>)
