package com.example.tdd.service

import com.example.tdd.CategoryEnum
import com.example.tdd.OrderStatusEnum
import com.example.tdd.UserRoleEnum
import com.example.tdd.dto.request.OrderRequestDto
import com.example.tdd.model.Order
import com.example.tdd.model.User
import com.example.tdd.model.seller.Product
import com.example.tdd.model.seller.Store
import com.example.tdd.repository.OrderDetailRepository
import com.example.tdd.repository.OrderRepository
import com.example.tdd.repository.ProductRepository
import com.example.tdd.repository.UserRepository
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OrderServiceUnitTest {


    private val user = User("유저이름", "비밀번호", UserRoleEnum.USER, 10000)
    private val store = Store(
        user = user,
        account = 123L,
        revenue = 1)
    private val userRepository: UserRepository = mockk()
    private val productRepository: ProductRepository = mockk()
    private val orderRepository: OrderRepository = mockk()
    private val orderDetailRepository: OrderDetailRepository = mockk()
    private val orderService = OrderService(userRepository, productRepository, orderRepository, orderDetailRepository)

    @Test
    @DisplayName("주문내역 생성")
    fun 주문내역_생성(){
        //given
        var product1 = Product(store, CategoryEnum.CATEGORY1.name, "상품명", 15000, 20, "설명")
        var product2 = Product(store, CategoryEnum.CATEGORY1.name, "상품명2", 20000, 30, "설명2")
        product1.id = 1L
        product2.id = 2L
        val productList = mutableListOf(product1, product2)
        val order = Order(
            user = user,
            status = OrderStatusEnum.PAYMENT_WAITING
        )

        val requestMap = mutableMapOf(
            1L to 3L,
            2L to 1L
        )
        val orderRequestDto = OrderRequestDto(requestMap)
        //when
        val result = orderService.buildOrderDetailList(order, orderRequestDto, productList)
        //then
        assertTrue(result.size == 2)
        assertTrue(result[0].quantity == 3L)
        assertTrue(result[0].product.stockLeft == 17L)
    }

    @Test
    @DisplayName("상품 재고를 초과한 주문")
    fun 상품_재고_초과_주문(){
        //given
        var product = Product(store, CategoryEnum.CATEGORY1.name, "상품명", 15000, 20, "설명")
        product.id = 1L
        val productList = mutableListOf(product)
        val order = Order(
            user = user,
            status = OrderStatusEnum.PAYMENT_WAITING
        )

        val requestMap = mutableMapOf(
            1L to 25L
        )
        val orderRequestDto = OrderRequestDto(requestMap)
        //when
        //then
        assertThrows<Exception>(message = "${product.name} 의 재고가 부족합니다. 남은 수량: ${product.stockLeft}"){
            orderService.buildOrderDetailList(order, orderRequestDto, productList)
        }
    }
}