package com.stori.creditfacade;

import com.stori.datamodel.Money;

public interface CreditService {
    /**
     * add new credit used record for the credit card
     *
     * @param creditCardId the credit card id
     * @param creditUsed the credit utilization
     * @return whether the operation is successful or not
     */
    Boolean updateCreditUsed(Long creditCardId, Money creditUsed, Long requestId);


    /**
     * release credit for the credit card
     *
     * @param creditCardId the credit card id
     * @param creditReleased the credit utilization released in this operation
     * @return whether the operation is successful or not
     */
    Boolean updateCreditReleased(Long creditCardId, Money creditReleased, Long requestId);


    /**
     * @return the remaining credit if credit card id is valid otherwise return null
     */
    Money getRemainingCredit(Long creditCardId);

}
