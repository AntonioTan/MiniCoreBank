package com.stori.orderservicefacade;


import com.stori.datamodel.Money;
import com.stori.datamodel.model.BizOrder;

public interface OrderService {
    /**
     * create a new order
     *
     * @param creditCardId credit card id
     * @param merchantId   merchant id
     * @param amount       amount of the order
     * @return order id if the operation is successful otherwise null
     */
    Long createOrder(Long creditCardId, Long merchantId, Money amount, Long requestId);


    /**
     * cancel an order
     *
     * @param orderId the order id
     * @return whether the operation is successful or not
     */
    Boolean cancelOrder(Long orderId, Long requestId);

    /**
     * @param orderId order id
     * @return the found order
     */
     BizOrder getOrder(Long orderId);
}

