package com.stori.creditfacade;

public interface CreditService {
    /**
     * add new credit used record for the credit card
     *
     * @param creditCardId the credit card id
     * @param creditUsed the credit utilization
     * @return whether the operation is successful or not
     */
    public abstract Boolean updateCreditUsed(Long creditCardId, Integer creditUsed, Long requestId);


    /**
     * release credit for the credit card
     *
     * @param creditCardId the credit card id
     * @param creditReleased the credit utilization released in this operation
     * @return whether the operation is successful or not
     */
    public abstract Boolean updateCreditReleased(Long creditCardId, Integer creditReleased, Long requestId);


    /**
     * @return the remaining credit if credit card id is valid otherwise return -1
     */
    public abstract Integer getRemainingCredit(Long creditCardId);

}
