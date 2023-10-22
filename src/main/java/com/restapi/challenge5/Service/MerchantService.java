package com.restapi.challenge5.Service;

import com.restapi.challenge5.Enum.MerchantStatus;
import com.restapi.challenge5.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MerchantService {

    List<Merchant> getAllMerchant();

    List<Merchant> getMerchantByStatus(MerchantStatus merchantStatus);
    Boolean addNewMerchant(Merchant merchant);

    Optional<Merchant> findById(String id);

    void deleteMerchantById(String merchant);

    void updateStatusMerchant(Merchant merchant);


//    Page<Merchant> getMerchantPaged(int page);

}
