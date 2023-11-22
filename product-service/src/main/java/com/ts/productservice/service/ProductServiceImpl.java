package com.ts.productservice.service;

import com.ts.common.exception.NotFoundException;
import com.ts.productservice.dto.ProductRequest;
import com.ts.productservice.dto.ProductResponse;
import com.ts.productservice.model.Product;
import com.ts.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        var products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(Pageable page) {
        return productRepository.findAll(page).map(this::mapToProductResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductResponse> findById(String id) {
        return productRepository.findById(id).map(this::mapToProductResponse);
    }

    @Override
    public void update(String id, ProductRequest request) {
        productRepository.findById(id)
                .ifPresentOrElse(product -> {
                    if (StringUtils.hasText(request.title())) product.setTitle(request.title());
                    if (StringUtils.hasText(request.description())) product.setDescription(request.description());
                    if (StringUtils.hasText(request.category())) product.setCategory(request.category());
                    if (StringUtils.hasText(request.image())) product.setImage(request.image());
                    if (request.price() != null) product.setPrice(request.price());
                    productRepository.save(product);
                }, () -> {
                    throw new NotFoundException("Product not found");
                });
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> findAllIds() {
        return productRepository.findAll().stream().map(Product::getId).toList();
    }

    @Override
    public void createProduct(ProductRequest request) {
        var product = Product.builder()
                .title(request.title())
                .description(request.description())
                .price(request.price())
                .category(request.category())
                .image(request.image())
                .build();
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }


    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .category(product.getCategory())
                .build();
    }
}

