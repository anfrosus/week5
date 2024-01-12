package com.example.tdd.model.buyer

import com.example.tdd.model.User
import jakarta.persistence.*

@Entity
@Table(name = "DELIVERY")
class Delivery(

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    var user: User,

    @Column(name = "DELIVERY_NAME")
    var name: String,

    @Column(name = "DELIVERY_POSTAL_CODE")
    var postalCode: String,

    @Column(name = "DELIVERY_ADDRESS")
    var address: String,

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}