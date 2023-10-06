package com.binarfud.challenge4.service;

import com.binarfud.challenge4.model.Merchant;
import com.binarfud.challenge4.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MerchantService {
    List<Merchant> getOpenMerchant();

    Boolean updateMerchant(Long merchantCode, String merchantName, String merchantLocation, Boolean open);

    Boolean addNewMerchant(Merchant merchant);

    Page<Merchant> getMerchantPaged(int page);
}
