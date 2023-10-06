package com.binarfud.challenge4.repository;

import com.binarfud.challenge4.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(nativeQuery = true,
            value = "select * from product order by product_code asc")
    List<Product> getAllProductAvailable();

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,
            value = "UPDATE product SET product_name = :product_name, price = :price WHERE product_code = :product_code")
    int updateById(@Param("product_code") Long product_code, @Param("product_name") String product_name, @Param("price") Long price);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true, value = "delete from product where product_code = :product_code")
    int deleteProduct(@Param("product_code") long product_code);

    @Query(nativeQuery = true, value = "select * from product")
    Page<Product> findAllWithPaging(Pageable pageable);

}
