package com.example.tdd.repository

import com.example.tdd.model.Order
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface OrderRepository: JpaRepository<Order, Long> {
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select o from Order o where o.id = :orderId")
    fun findByIdWithPessimisticLock(orderId: Long): Order?
}
