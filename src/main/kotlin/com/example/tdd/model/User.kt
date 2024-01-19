package com.example.tdd.model

import com.example.tdd.UserRoleEnum
import jakarta.persistence.*

@Entity
@Table(name = "USERS")
class User(

    @Column(name = "USER_NAME", nullable = false)
    var userName: String,

    @Column(name = "user_PASSWORD", nullable = false)
    var password: String,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "USER_ROLE", nullable = false)
    var role: UserRoleEnum,

    @Column(name = "USER_BALANCE", nullable = false)
    var balance: Long = 0

    //이관하여 저장할 껀데 orphanRemoval 굳이?
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
//    var favoriteList: MutableSet<Favorite>,
//
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
//    var cartList: MutableSet<Cart>
//    배송 주소도 굳이 양방향?

    ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun checkSellerOrThrow(){
        if(role != UserRoleEnum.SELLER){
            throw Exception("판매자 권한이 없습니다.")
        }
    }

    fun reduceBalance(totalPrice: Long) {
        if (totalPrice > balance) {
            throw Exception("잔액이 부족합니다.")
        }
        balance -= totalPrice
    }
}