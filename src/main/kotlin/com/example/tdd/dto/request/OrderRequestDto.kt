package com.example.tdd.dto.request

data class OrderRequestDto(
    var productRequestList: MutableMap<Long, Long>
) {

}
