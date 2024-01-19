package com.example.tdd.integration_test

import IntegrationTest
import com.example.tdd.repository.OrderDetailRepository
import com.example.tdd.repository.OrderRepository
import com.example.tdd.repository.ProductRepository
import com.example.tdd.repository.UserRepository
import com.example.tdd.service.OrderService
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

class OrderServiceTest(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val productRepository: ProductRepository,
    @Autowired
    private val orderRepository: OrderRepository,
    @Autowired
    private val orderDetailRepository: OrderDetailRepository,
    @Autowired
    private val orderService: OrderService
) : IntegrationTest() {
    @Test
    @DisplayName("상품 동시 주문 테스트")
    fun 상품_동시_주문() {
//        orderService.buy(1, 1)
        val user = userRepository.findByIdOrNull(1L)!!
        val numberOfThreads = 4
        val executor = Executors.newFixedThreadPool(numberOfThreads)
        val latch = CountDownLatch(4)

        executor.execute {
            orderService.buy(1, 1)
            latch.countDown()
        }
        executor.execute {
            orderService.buy(2, 2)
            latch.countDown()
        }
        executor.execute {
            orderService.buy(3, 3)
            latch.countDown()
        }
        executor.execute {
            orderService.buy(4, 4)
            latch.countDown()
        }
        latch.await()

        println("product stock left =======================")
        assertTrue(productRepository.findByIdOrNull(1L)!!.stockLeft == 8L)
        assertTrue(userRepository.findByIdOrNull(1)!!.balance == 4000L)
        assertTrue(userRepository.findByIdOrNull(2)!!.balance == 5000L)
        assertTrue(userRepository.findByIdOrNull(3)!!.balance == 6000L)
        assertTrue(userRepository.findByIdOrNull(4)!!.balance == 7000L)
    }
}