package com.stori.creditfacade;

public interface CreditBase {

    /**
     * @return the id of the credit card
     */
    public abstract long getCreditCardId();

    /**
     * set the credit limit for the credit card
     *
     * @param creditLimit the init credit set for the credit card
     * @return whether the operation is successful or not
     */
    public abstract boolean setCreditLimit(int creditLimit);

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
