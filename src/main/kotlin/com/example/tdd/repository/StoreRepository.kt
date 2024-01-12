package com.example.tdd.repository

import com.example.tdd.model.seller.Store
import org.springframework.data.jpa.repository.JpaRepository

interface StoreRepository: JpaRepository<Store, Long> {
    fun findStoresByUserId(userId: Long): MutableList<Store>
}