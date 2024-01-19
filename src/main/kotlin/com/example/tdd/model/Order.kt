package com.example.tdd.model

import com.example.tdd.OrderStatusEnum
import com.example.tdd.dto.response.OrderResponseDto
import jakarta.persistence.*

@Entity
@Table(name = "ORDERS")
class Order(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    var user: User,

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    var orderDetailList: MutableList<OrderDetail> = mutableListOf(),

    var totalPrice: Long = 0,

    @Column(name = "ORDER_STATUS")
    @Enumerated(value = EnumType.STRING)
    var status: OrderStatusEnum

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun addOrderDetail(orderDetail: OrderDetail){
        orderDetailList.add(orderDetail)
    }

    fun cancel() {
        if (status != OrderStatusEnum.PAYMENT_WAITING || status != OrderStatusEnum.DELIVERY_WAITING){
            throw Exception("결제를 취소할 수 없는 상태입니다.")
        }
    }

    fun statusDeliveryWaiting(){
        status = OrderStatusEnum.DELIVERY_WAITING
    }

    fun addTotalPrice(price: Long) {
        totalPrice += price
    }

    fun checkStock() {
        orderDetailList.map {
            if (it.product.stockLeft < it.quantity) {
                throw Exception("재고가 부족스")
            }

        }
    }

    fun reduceStock() {
        orderDetailList.map {
            if (it.product.stockLeft < it.quantity){
                throw Exception("재고가 부족스")
            }
            it.product.reduceStock(it.quantity)
        }
    }

    fun getDetails(): MutableMap<Long, Long> {
        var map: MutableMap<Long, Long> = mutableMapOf()
        this.orderDetailList.map {
            map.put(it.product.id!!, it.quantity)
        }
        return map
    }
}

fun Order.toResponse(): OrderResponseDto{
    return OrderResponseDto(
        orderId = id!!
    )
}
fun Order.toMap(): Map<Long, Long>{
    return mutableMapOf()
}