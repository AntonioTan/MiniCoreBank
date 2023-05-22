package com.stori.bankuserservicefacade;

public interface CreditCardBase {

    /**
     * set the status for the user
     * @param creditCardId the id of the credit card owned by the user
     * @param status the new status set for the credit card
     * @return whether the set operation is successful or not
     */
    public abstract boolean setCreditCardStatus(long creditCardId, CreditCardStatus status);

    /**
     * get the status of the credit card owned by the user
     * @param creditCardId the id of the credit card owned by the user
     * @return the status of the credit card owned by this user
     */
    public abstract CreditCardStatus getStatus(long creditCardId);
}
