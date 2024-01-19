package com.example.tdd.service

import com.example.tdd.OrderStatusEnum
import com.example.tdd.dto.request.OrderRequestDto
import com.example.tdd.dto.response.OrderResponseDto
import com.example.tdd.model.Order
import com.example.tdd.model.OrderDetail
import com.example.tdd.model.User
import com.example.tdd.model.seller.Product
import com.example.tdd.model.toResponse
import com.example.tdd.repository.OrderDetailRepository
import com.example.tdd.repository.OrderRepository
import com.example.tdd.repository.ProductRepository
import com.example.tdd.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
    private val orderDetailRepository: OrderDetailRepository
) {
    @Transactional
    fun createOrder(userId: Long, orderRequestDto: OrderRequestDto): OrderResponseDto {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw Exception("존재하지 않는 userId 입니다.")
        val order = Order(
            user = user,
            status = OrderStatusEnum.PAYMENT_WAITING
        )
        orderRepository.save(order)

        val productList = productRepository.findProductsByIdIn(orderRequestDto.productRequestList.keys.toList())
        orderDetailRepository.saveAll(
            buildOrderDetailList(order, orderRequestDto, productList)
        )

        return order.toResponse()
    }

    @Transactional
    fun buy(userId: Long, orderId: Long) {
        val order = orderRepository.findByIdOrNull(orderId)
            ?: throw Exception("orderId")
        val user = userRepository.findByIdOrNull(userId)
            ?: throw Exception("userId")

        if (order.user.id != user.id) {
            throw Exception("")
        }

        val productIdQuantityMap = order.getDetails()
        val productList = productRepository.findProductsByIdInWithLock(productIdQuantityMap.keys.toList())
        checkStock(productList, productIdQuantityMap)

        payment(order, user)
        paymentComplete(order, productList, productIdQuantityMap)
    }

    @Transactional
    fun paymentComplete(order: Order, productList: MutableList<Product>, productIdQuantityMap: Map<Long, Long>) {
        productList.map {
            it.reduceStock(productIdQuantityMap[it.id]!!)
            println(it.stockLeft)
        }
        order.statusDeliveryWaiting()
    }

    fun checkStock(productList: List<Product>, map: Map<Long, Long>){
        productList.map { it.checkStock(map[it.id]!!) }
    }

    @Transactional
    fun payment(order: Order, user: User) {
        // try {
        //  iamport.pay(order.totalPirce)
        // catch (exception E) {
        // }
        user.reduceBalance(order.totalPrice)
    }

    @Transactional
    fun cancelOrder(orderId: Long) {
        var order: Order = orderRepository.findByIdOrNull(orderId)
            ?: throw Exception("존재하지 않는 orderId")
    }

    fun buildOrderDetailList(order: Order, orderRequestDto: OrderRequestDto, productList: List<Product>) : MutableList<OrderDetail>{
        if(orderRequestDto.productRequestList.size != productList.size){
            throw Exception("존재하지 않는 productId 가 포함되어 있습니다.")
        }

        return productList.map {


            val requestQuantity = orderRequestDto.productRequestList[it.id]!!
            order.addTotalPrice(it.price * requestQuantity)
            OrderDetail(
                order = order,
                product = it,
                quantity = requestQuantity
            )
        }.toMutableList()
    }
}