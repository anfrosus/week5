package com.example.tdd.dto.request

data class OrderRequestDto(
    var productRequestList: Map<Long, Long>
) {

}
