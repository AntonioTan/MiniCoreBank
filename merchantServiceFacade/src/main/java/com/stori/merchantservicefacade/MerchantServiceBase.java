package com.stori.merchantservicefacade;

import com.stori.datamodel.model.Merchant;

public interface MerchantServiceBase {

    /**
     * @param merchantId a valid merchant id
     * @return the found merchant
     */
    Merchant getMerchant(Long merchantId);
}
