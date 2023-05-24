package com.stori.bankuserservicefacade;

public interface UserServiceBase {


    /**
     * open a new user account
     * @param name the user name
     * @return whether the set operation is successful or not
     */
    public abstract boolean addUser(String name);

    /**
     * send request to credit module to set the credit limit
     *
     * @param creditCardId the id of the credit card owned by the user
     * @param creditLimit  the new credit limit for the credit card
     * @return whether the set operation is successful or not
     */
    public abstract boolean setCreditLimit(long creditCardId, int creditLimit);


    /**
     * send request to credit module to get the remaining credit limit
     *
     * @param creditCardId the id of the credit card owned by the user
     * @return the available credit of the credit card owned by the user
     */
    public abstract int getAvailableCredit(long creditCardId);


    /**
     * send request account module to get the account balance
     *
     * @param userId the user id
     * @return the account balance of the user account
     */
    public abstract int getAccountBalance(long userId);

    /**
     * @param userId the user id
     * @return whether the operation is successful or not
     */
    public abstract boolean addCreditCard(long userId);
}
