package com.example.tdd.dto.response

data class ProductResponseDto(
    var productId: Long,
    var productName: String,
    var productCategory: String,
    var productPrice: Long,
    var productDescription: String,
    var productStockLeft: Long
)
