package com.example.tdd.service

import com.example.tdd.dto.request.ProductRequestDto
import com.example.tdd.model.User
import com.example.tdd.model.seller.Product
import com.example.tdd.model.seller.Store
import com.example.tdd.repository.StoreRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val storeRepository: StoreRepository,
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
) {
    @Transactional
    fun createProduct(userId: Long, productRequestDto: ProductRequestDto) : ProductResponseDto{
        //유저가 판매자인건 컨트롤러에서 검증
        val user: User = userRepository.findByIdOrNull(userId)
            ?: throw CustomException("존재하지 않는 userId 입니다.")

        user.checkSellerOrThrow()

        val store: Store = storeRepository.findByIdOrNull(productRequestDto.storeId)
            ?: throw CustomException("존재하지 않는 storeId 입니다.")

        //상점을 개설했다면 상품등록가능
        val newProduct: Product = buildProduct(store, productRequestDto)

        return productRepository.save(product).toResponse()
    }

    private fun buildProduct(store: Store, productRequestDto: ProductRequestDto): Product {
        return Product(
            store = store,
            category = productRequestDto.category,
            name = productRequestDto.name,
            price = productRequestDto.price,
            stockLeft = productRequestDto.stockLeft,
            description = productRequestDto.description
        )
    }

}