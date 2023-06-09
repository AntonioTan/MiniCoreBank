package com.stori.merchantservice;

import com.stori.datamodel.model.Merchant;
import com.stori.datamodel.repository.MerchantRepository;
import com.stori.merchantservicefacade.MerchantServiceBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import java.util.Optional;

public class MerchantService implements MerchantServiceBase {
    private final static Logger logger = LogManager.getLogger(MerchantService.class);
    @Resource
    private MerchantRepository merchantRepository;

    @Override
    public Merchant getMerchant(Long merchantId) {
        Optional<Merchant> optionalMerchant = merchantRepository.findById(merchantId);
        if(optionalMerchant.isPresent()) {
            logger.info("Found merchant with id: " + merchantId);
            return optionalMerchant.get();
        } else {
            logger.warn("Failed to find merchant with id: " + merchantId);
            return null;
        }
    }
}
