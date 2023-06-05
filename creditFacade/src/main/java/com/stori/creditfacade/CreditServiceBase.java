package com.stori.creditfacade;

public interface CreditServiceBase {
    /**
     * add new credit used record for the credit card
     *
     * @param creditCardId the credit card id
     * @param creditUsed the credit utilization
     */
    public abstract void addCreditUsed(long creditCardId, int creditUsed);


    /**
     * release credit for the credit card
     *
     * @param creditCardId the credit card id
     * @param creditReleased the credit utilization released in this operation
     */
    public abstract void releaseCredit(long creditCardId, int creditReleased);


    /**
     * @return the remaining credit if credit card id is valid otherwise return -1
     */
    public abstract int getRemainingCredit(long creditCardId);

}
