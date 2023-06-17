package com.stori.datamodel.model;


import com.stori.datamodel.OrderStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BizOrder")
public class BizOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name="credit_card_id", referencedColumnName = "id", nullable = false)
    private CreditCard creditCard;

    @OneToOne
    @JoinColumn(name="merchant_id", referencedColumnName = "id", nullable = false)
    private Merchant merchant;

    @Column(updatable = false, nullable = false)
    private int amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, nullable = false)
    private Date timestamp;


    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    public BizOrder() {
        this.timestamp = new Date();
        this.orderStatus = OrderStatus.ACTIVE;
    }


    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public Long getId() {
        return id;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public int getAmount() {
        return amount;
    }
}
