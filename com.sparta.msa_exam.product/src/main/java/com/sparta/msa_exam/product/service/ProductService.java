package com.sparta.msa_exam.product.service;

import com.sparta.msa_exam.product.dto.ProductRequestDto;
import com.sparta.msa_exam.product.dto.ProductResponseDto;
import com.sparta.msa_exam.product.mapper.ProductMapper;
import com.sparta.msa_exam.product.products.Product;
import com.sparta.msa_exam.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper = ProductMapper.INSTANCE;


    // 상품 추가
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        // Dto -> Entity
        Product product = productMapper.toEntity(requestDto);
        // Entity -> DB
        product = productRepository.save(product);
        // 저장된 Entity를 Dto로 변환
        return productMapper.toDto(product);
    }

    // 모든 상품 조회
    public List<ProductResponseDto> getProducts() {
        List<Product> products = productRepository.findAll();
        // Entity list -> Dto List로 변환
        return productMapper.toDtoList(products);
    }

    // 단건 조회
    public ProductResponseDto getProduct(Long productId) {
        // ProductId를 조회하고, DB에 없는 경우 예외 처리
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product Id not found : " + productId));
        // 조회된 Product를 DTO로 변환
        return productMapper.toDto(product);
    }
}
