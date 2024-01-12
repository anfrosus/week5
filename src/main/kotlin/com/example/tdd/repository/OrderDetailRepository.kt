package com.example.tdd.repository

import com.example.tdd.model.OrderDetail
import org.springframework.data.jpa.repository.JpaRepository

interface OrderDetailRepository: JpaRepository<OrderDetail, Long> {

}
