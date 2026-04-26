package com.ecommerce.service;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product createProduct(ProductDto productDto);

    Product updateProduct(Long id, ProductDto productDto);

    void deleteProduct(Long id);

    Optional<Product> findById(Long id);

    Optional<Product> findBySku(String sku);

    Page<Product> findAllActiveProducts(Pageable pageable);

    Page<Product> findProductsByCategory(Long categoryId, Pageable pageable);

    Page<Product> searchProducts(String keyword, Pageable pageable);

    Page<Product> findProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    List<Product> findLowStockProducts(Integer threshold);

    ProductDto convertToDto(Product product);

    List<ProductDto> convertToDtoList(List<Product> products);
}