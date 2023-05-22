package com.stori.accountfacade;

public interface accountBase {

    /**
     * The method get the account balance
     *
     * @return the account balance of the user account
     */
    public abstract int getAccountBalance();

    /**
     * The method add new credit card record to the account
     *
     * @param creditCardRecordBase new credit card record
     * @return whether the operation is successful or not
     */
    public abstract  boolean addCreditCardRecord(CreditCardRecordBase creditCardRecordBase);
}
