package com.stori.creditfacade;

public interface CreditServiceBase {
    /**
     * add new credit utilization record for the credit card
     *
     * @param creditUtilization the credit utilization
     * @return whether the operation is successful or not
     */
    public abstract boolean addCreditUtilization(int creditUtilization);


    /**
     * release credit for the credit card
     *
     * @param creditUtilization the credit utilization released in this operation
     * @return whether the operation is successful or not
     */
    public abstract boolean releaseCreditUtilization(int creditUtilization);


    /**
     * @return the remaining credit
     */
    public abstract int getRemainingCredit();

}
