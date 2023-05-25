package com.stori.bankuserservicefacade;

public interface UserServiceBase {


    /**
     * open a new user account
     *
     * @param name the username
     * @return -1 if the operation is failed or the user id if otherwise
     */
    public abstract Long addUser(String name);


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
