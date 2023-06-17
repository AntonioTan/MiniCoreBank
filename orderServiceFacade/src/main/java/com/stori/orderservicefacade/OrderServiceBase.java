package com.stori.orderservicefacade;


import com.stori.datamodel.model.BizOrder;

public interface OrderServiceBase {
    BizOrder getOrder(Long orderId);
    /**
     * create a new order
     *
     * @param creditCardId credit card id
     * @param merchantId   merchant id
     * @param amount       amount of the order
     * @return order id if the operation is successful otherwise null
     */
    Long createOrder(Long creditCardId, Long merchantId, int amount, long requestId);


    /**
     * cancel an order
     *
     * @param orderId the order id
     * @return whether the operation is successful or not
     */
    boolean cancelOrder(Long orderId, Long requestId);

    /**
     * @param orderId order id
     * @return the found order
     */
     BizOrder findOrder(Long orderId);
}

