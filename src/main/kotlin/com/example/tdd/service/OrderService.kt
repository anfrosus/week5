package com.example.tdd.service

import com.example.tdd.OrderStatusEnum
import com.example.tdd.dto.request.OrderRequestDto
import com.example.tdd.model.Order
import com.example.tdd.model.OrderDetail
import com.example.tdd.model.User
import com.example.tdd.model.seller.Product
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
    fun createOrder(userId: Long, orderRequestDto: OrderRequestDto): Order {

        val user: User = userRepository.findByIdOrNull(userId)
            ?: throw Exception("존재하지 않는 userId 입니다.")

        val order = Order(
            user = user,
            status = OrderStatusEnum.PAYMENT_WAITING
        )
        orderRepository.save(order)

        val productList: List<Product> = productRepository.findProductsById(orderRequestDto.productRequestList.keys)
        val orderDetailList = buildOrderDetailList(order, orderRequestDto, productList)
        orderDetailRepository.saveAll(orderDetailList)

        user.checkBalance(order.totalPrice)

        return order
    }

    @Transactional
    fun cancelOrder(orderId: Long) {
        var order: Order = orderRepository.findByIdOrNull(orderId)
            ?: throw Exception("존재하지 않는 orderId")


    }

    fun buildOrderDetailList(order: Order, orderRequestDto: OrderRequestDto, productList: List<Product>) : MutableList<OrderDetail>{
//        val productList: List<Product> = productRepository.findProductListById(orderRequestDto.productRequestList.keys)
        if(orderRequestDto.productRequestList.size != productList.size){
            throw Exception("존재하지 않는 productId 가 포함되어 있습니다.")
        }

        return productList.map {

            val requestQuantity = orderRequestDto.productRequestList[it.id]!!
            it.reduceStock(requestQuantity)
            order.totalPrice += it.price

            OrderDetail(
                order = order,
                product = it,
                quantity = requestQuantity
            )
        }.toMutableList()
    }
}