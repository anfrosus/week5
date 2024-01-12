package com.example.tdd.model

import com.example.tdd.OrderStatusEnum
import jakarta.persistence.*

@Entity
@Table(name = "ORDER")
class Order(

    @ManyToOne
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
}