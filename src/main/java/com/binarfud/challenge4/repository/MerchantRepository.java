package com.binarfud.challenge4.repository;

import com.binarfud.challenge4.model.Merchant;
import com.binarfud.challenge4.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,
            value = "UPDATE merchant SET merchant_name = :merchant_name, nerchant_location = :merchant_location, open = :open WHERE merchant_code = :merchant_code")
    int updateById(@Param("merchant_code") Long merchant_code, @Param("merchant_name") String merchant_name, @Param("merchant_location") String merchant_location, @Param("open") Boolean open);

    @Query(nativeQuery = true, value = "select * from merchant")
    Page<Merchant> findAllWithPaging(Pageable pageable);

}
