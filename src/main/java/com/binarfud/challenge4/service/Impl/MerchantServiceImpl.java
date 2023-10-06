package com.binarfud.challenge4.service.Impl;

import com.binarfud.challenge4.model.Merchant;
import com.binarfud.challenge4.repository.MerchantRepository;
import com.binarfud.challenge4.service.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    private MerchantRepository merchantRepository;

    @Override
    public List<Merchant> getOpenMerchant() {
        try {
            log.info("Retrieving All Open Merchant");
            List<Merchant> merchantList = merchantRepository.findAll();
            return Optional.of(merchantList)
                    .map(list -> list.stream().filter(Merchant::isOpen).collect(Collectors.toList()))
                    .orElse(Collections.emptyList());
        } catch (Exception e) {
            log.error("Error while retrieving open merchants: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    @Override
    public Boolean updateMerchant(Long merchantCode, String merchantName, String merchantLocation, Boolean open) {
        try {
            log.info("Updating Merchant");
            Optional<Merchant> existingMerchant = merchantRepository.findById(merchantCode);

            if (existingMerchant.isPresent()) {
                int rowsUpdated = merchantRepository.updateById(merchantCode, merchantName, merchantLocation, open);
                return rowsUpdated > 0;
            } else {
                log.error("Merchant with code {} not found. Update failed.", merchantCode);
                return false;
            }
        } catch (Exception e) {
            log.error("Error while updating merchant: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean addNewMerchant(Merchant merchant) {
        try {
            if (merchant != null) {
                log.info("Saving new Merchant...");
                merchantRepository.save(merchant);
                return true;
            } else {
                log.error("Failed to save new Merchant: merchant is null");
                return false;
            }
        } catch (Exception e) {
            log.error("Error while adding a new merchant: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Page<Merchant> getMerchantPaged(int page) {
        try {
            log.info("Retrieving paged merchants for page {}.", (page + 1));
            return merchantRepository.findAllWithPaging(PageRequest.of(page, 3));
        } catch (Exception e) {
            log.error("Error while retrieving paged merchants: {}", e.getMessage(), e);
            return Page.empty();
        }
    }
}
