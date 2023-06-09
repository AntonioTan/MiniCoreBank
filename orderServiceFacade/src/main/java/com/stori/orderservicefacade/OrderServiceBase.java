package com.stori.orderservicefacade;


import com.stori.datamodel.model.Order;

public interface OrderServiceBase {
    Order getOrder(Long orderId);
    /**
     * create a new order
     *
     * @param creditCardId credit card id
     * @param merchantId   merchant id
     * @param amount       amount of the order
     * @return whether the operation is successful or not
     */
    boolean createOrder(Long creditCardId, Long merchantId, int amount);


    /**
     * cancel an order
     *
     * @param orderId the order id
     * @return whether the operation is successful or not
     */
    boolean cancelOrder(Long orderId);

    /**
     * @param orderId order id
     * @return the found order
     */
     Order findOrder(Long orderId);
}

