package com.example.tdd.dto.request

data class ProductRequestDto(
    var storeId: Long,
    var category: String,
    var name: String,
    var price: Long,
    var stockLeft: Long,
    var description: String
) {

}
