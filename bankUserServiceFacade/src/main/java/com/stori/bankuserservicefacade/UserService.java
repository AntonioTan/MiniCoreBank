package com.stori.bankuserservicefacade;

/**
 * The interface defines behavior to provide user-related service
 */
public interface UserService {


    /**
     * open a new user account
     *
     * @param name the username
     * @return -1 if operation failed or user id if otherwise
     */
    Long saveUser(String name);


    /**
     * @param userId user id
     */
    void removeUser(Long userId);


    /**
     * send request account module to get the account balance
     *
     * @param userId the user id
     * @return the account balance of the user account
     */
    Integer getAccountBalance(Long userId);

    /**
     * @param userId the user id
     * @return credit card id if operation is successful otherwise return -1
     */
    Long saveCreditCard(Long userId);

}
