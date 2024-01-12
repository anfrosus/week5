package com.example.tdd.model

import com.example.tdd.model.seller.Product
import jakarta.persistence.*

@Entity
@Table(name = "ORDER_DETAIL")
class OrderDetail(

    @ManyToOne
    @JoinColumn(name = "ORDER_ID", nullable = false)
    var order: Order,

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    var product: Product,

    @Column(name = "QUANTITY", nullable = false)
    var quantity: Long,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    init{
        order.addOrderDetail(this)
    }
}