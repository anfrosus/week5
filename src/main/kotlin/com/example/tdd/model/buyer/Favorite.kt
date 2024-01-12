package com.example.tdd.model.buyer

import com.example.tdd.model.seller.Product
import com.example.tdd.model.User
import jakarta.persistence.*

@Entity
@Table(name = "BOOKMARK")
class Favorite(

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    var user: User,

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    var product: Product

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}