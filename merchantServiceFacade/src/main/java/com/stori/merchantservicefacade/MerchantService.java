package com.stori.merchantservicefacade;

import com.stori.datamodel.model.Merchant;

/**
 * The interface defines behavior to provide merchant-related service
 */
public interface MerchantService {

    /**
     * @param merchantId a valid merchant id
     * @return the found merchant
     */
    Merchant getMerchant(Long merchantId);

    Long saveMerchant(String name);
}
