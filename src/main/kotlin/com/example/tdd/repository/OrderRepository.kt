package com.example.tdd.repository

import com.example.tdd.model.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository: JpaRepository<Order, Long> {

}
