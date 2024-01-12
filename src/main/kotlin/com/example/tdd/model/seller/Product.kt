package com.example.tdd.model.seller

import jakarta.persistence.*

    @Entity
    @Table(name = "PRODUCT")
    class Product(

        @ManyToOne
        @JoinColumn(name = "STORE_ID", nullable = false)
        var store: Store,

        @Column(name = "PRODUCT_CATEGORY", nullable = false)
        var category: String,

        @Column(name = "PRODUCT_NAME", nullable = false)
        var name: String,

        @Column(name = "PRODUCT_PRICE", nullable = false)
        var price: Long,

        @Column(name = "PRODUCT_STOCK_LEFT", nullable = false)
        var stockLeft: Long,

        @Column(name = "PRODUCT_DESCRIPTION", nullable = false)
        @Lob
        var description: String,

        ) {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null

        init {
            store.addProduct(this)
        }

        fun reduceStock(requestQuantity: Long){
            if (requestQuantity > stockLeft) {
                throw Exception("${name} 의 재고가 부족합니다. 남은 수량: ${stockLeft}")
            }
            stockLeft -= requestQuantity
        }

        fun restoreStock(restoreQuantity: Long){
            stockLeft += restoreQuantity
        }
    }
