package com.sparta.msa_exam.product.mapper;

import com.sparta.msa_exam.product.dto.ProductRequestDto;
import com.sparta.msa_exam.product.dto.ProductResponseDto;
import com.sparta.msa_exam.product.products.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    // Entity -> Dto
    ProductResponseDto toDto(Product product);

    // Dto -> Entity
    Product toEntity(ProductRequestDto requestDto);

    // 엔티티 리스트에서 DTO 리스트로 변환
    List<ProductResponseDto> toDtoList(List<Product> products);
}
