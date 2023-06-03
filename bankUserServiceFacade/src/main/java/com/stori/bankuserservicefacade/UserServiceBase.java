package com.stori.bankuserservicefacade;

public interface UserServiceBase {


    /**
     * open a new user account
     *
     * @param name the username
     * @return -1 if operation failed or user id if otherwise
     */
    public abstract Long addUser(String name);


    /**
     * @param userId user id
     */
    public abstract void removeUser(Long userId);


    /**
     * send request account module to get the account balance
     *
     * @param userId the user id
     * @return the account balance of the user account
     */
    public abstract int getAccountBalance(long userId);

    /**
     * @param userId the user id
     * @return credit card id if operation is successful otherwise return -1
     */
    public abstract Long addCreditCard(long userId);

}
