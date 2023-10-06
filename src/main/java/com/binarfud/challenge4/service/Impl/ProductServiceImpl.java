package com.binarfud.challenge4.service.Impl;

import com.binarfud.challenge4.model.Product;
import com.binarfud.challenge4.repository.ProductRepository;
import com.binarfud.challenge4.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAllProduct() {
        try {
            log.info("Retrieving all available products.");
            return productRepository.getAllProductAvailable();
        } catch (Exception e) {
            log.error("Error while retrieving all products: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public Boolean addNewProduct(Product product) {
        try {
            log.info("Adding a new product.");
            return Optional.ofNullable(product)
                    .map(newProduct -> productRepository.save(newProduct))
                    .map(Objects::nonNull)
                    .orElseGet(() -> {
                        log.error("Failed adding a new Product");
                        return Boolean.FALSE;
                    });
        } catch (Exception e) {
            log.error("Error while adding a new product: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean updateById(Long product_code, String product_name, Long price) {
        try {
            log.info("Updating product with code {}.", product_code);
            int rowsUpdated = productRepository.updateById(product_code, product_name, price);
            return rowsUpdated > 0;
        } catch (Exception e) {
            log.error("Error while updating product: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean deleteProductbyId(Long product_code) {
        try {
            log.info("Deleting product with code {}.", product_code);
            int rowsDeleted = productRepository.deleteProduct(product_code);
            return rowsDeleted > 0;
        } catch (Exception e) {
            log.error("Error while deleting product: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Page<Product> getProductPaged(int page) {
        try {
            log.info("Retrieving paged products for page {}.", (page + 1));
            return productRepository.findAllWithPaging(PageRequest.of(page, 2));
        } catch (Exception e) {
            log.error("Error while retrieving paged products: {}", e.getMessage(), e);
            return Page.empty();
        }
    }
}
