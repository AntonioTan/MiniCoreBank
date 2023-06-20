package com.stori.bankuserservicefacade;

import com.stori.datamodel.CreditCardStatusEnum;
import com.stori.datamodel.model.CreditCard;

public interface CreditCardService {
    CreditCard getCreditCard(Long creditCardId);
    /**
     * set the status for the user
     * @param creditCardId the id of the credit card owned by the user
     * @param status the new status set for the credit card
     * @return whether the set operation is successful or not
     */
    public abstract Boolean updateCreditCardStatus(Long creditCardId, CreditCardStatusEnum status);

    /**
     * get the status of the credit card owned by the user
     * @param creditCardId the id of the credit card owned by the user
     * @return the status of the credit card owned by this user
     */
    public abstract CreditCardStatusEnum getStatus(Long creditCardId);


    /**
     * send request to credit module to set the credit limit
     *
     * @param creditCardId the id of the credit card owned by the user
     * @param creditLimit  the new credit limit for the credit card
     * @return whether the set operation is successful or not
     */
    public abstract Boolean setCreditLimit(Long creditCardId, Integer creditLimit);

    /**
     * send request to credit module to get the current credit limit
     *
     * @param creditCardId the id of the credit card owned by the user
     * @return the current credit limit of the credit card owned by the user
     */
    public abstract Integer getCreditLimit(Long creditCardId);

}
