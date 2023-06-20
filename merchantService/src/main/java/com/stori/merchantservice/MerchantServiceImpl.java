package com.stori.merchantservice;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.stori.datamodel.model.Merchant;
import com.stori.datamodel.repository.MerchantRepository;
import com.stori.merchantservicefacade.MerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
@SofaService(uniqueId = "merchantService")
public class MerchantServiceImpl implements MerchantService {
    private final static Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);
    @Resource
    private MerchantRepository merchantRepository;

    @Override
    public Merchant getMerchant(Long merchantId) {
        Optional<Merchant> optionalMerchant = merchantRepository.findById(merchantId);
        if(optionalMerchant.isPresent()) {
            if(logger.isDebugEnabled()) logger.info("Found merchant with id: " + merchantId);
            return optionalMerchant.get();
        } else {
            logger.warn("Failed to find merchant with id: " + merchantId);
            return null;
        }
    }

    @Override
    public Long saveMerchant(String name) {
        Merchant merchant = new Merchant(name);
        merchantRepository.save(merchant);
        return merchant.getId();
    }


}
