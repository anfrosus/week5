package com.example.tdd.repository

import com.example.tdd.model.seller.Product
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface ProductRepository: JpaRepository<Product, Long> {
    @Query("select p from Product p where p.id in :keys")
    fun findProductsByIdIn(keys: List<Long>): MutableList<Product>
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.id in :productIdList")
    fun findProductsByIdInWithLock(productIdList: List<Long>): MutableList<Product>

}
