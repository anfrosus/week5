package com.example.tdd.model.buyer

import com.example.tdd.model.seller.Product
import com.example.tdd.model.User
import jakarta.persistence.*

@Entity
@Table(name = "CART")
class Cart(

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    var user: User,

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    var product: Product,

    @Column(name = "PRODUCT_QUANTITY", nullable = false)
    var quantity: Long

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}