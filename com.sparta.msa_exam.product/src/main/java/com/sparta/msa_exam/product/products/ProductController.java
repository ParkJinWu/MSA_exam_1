package com.sparta.msa_exam.product.products;

import com.sparta.msa_exam.product.dto.ProductRequestDto;
import com.sparta.msa_exam.product.dto.ProductResponseDto;
import com.sparta.msa_exam.product.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    //Load Balancing Test API
    @Value("${server.port}") // 애플리케이션이 실행 중인 포트를 주입받습니다.
    private String serverPort;

    @GetMapping("/test")
    public String getProduct() {
        return "Product info!!!!! From port : " + serverPort;
    }

    /*
    * 상품 추가 API
    * 상품 Entity : product_id = Long(Primary,Auto Increment)
    *              name = String
    *              supply_price =Integer
    *  */
    @PostMapping
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto, HttpServletResponse response) {
        return productService.createProduct(requestDto);
    }


    /*
    * 상품 목록 조회 API
    * 응답 형태 : List
    * 응답 객체 : product_id = Long
    *           name = String
    *           supply_price = Integer
    * */
    @GetMapping
    public List<ProductResponseDto> getProducts(HttpServletResponse response) {
        return productService.getProducts();
    }

    /*
    * 주문 단건 조회 API
    * 응답 객체 : order_id = Long
    *           product_ids = List<Long>
    */
    @GetMapping("/{productId}")
    public ProductResponseDto getProduct(@PathVariable Long productId, HttpServletResponse response) {
        return productService.getProduct(productId);
    }

}
