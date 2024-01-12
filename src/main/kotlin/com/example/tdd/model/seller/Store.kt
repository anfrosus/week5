package com.example.tdd.model.seller

import com.example.tdd.model.User
import jakarta.persistence.*

@Entity
@Table(name = "STORE")
class Store(

    @ManyToOne
    @JoinColumn
    var user: User,

    @Column(name = "STORE_ACCOUNT", nullable = false)
    var account: Long,

    @OneToMany(mappedBy = "store")
    var productList: MutableList<Product> = mutableListOf(),

    @Column(name = "STORE_REVENUE")
    var revenue: Long

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun addProduct(product: Product){
        productList.add(product)
    }
}