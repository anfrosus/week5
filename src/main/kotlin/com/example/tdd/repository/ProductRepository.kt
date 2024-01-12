package com.example.tdd.repository

import com.example.tdd.model.seller.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository: JpaRepository<Product, Long> {
    fun findProductsById(keys: Set<Long>): MutableList<Product>

}
