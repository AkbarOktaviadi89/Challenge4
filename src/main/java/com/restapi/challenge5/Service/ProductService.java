package com.restapi.challenge5.Service;

import com.restapi.challenge5.Model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {

    List<Product> findAllProduct();

    Optional<Product> findById(String id);
    Boolean addNewProduct(Product product);

    void updateById(Product product);

    void deleteProductbyId(String product_code);

    void deleteAllProduct();

//    Page<Product> getProductPaged(int page);
}
