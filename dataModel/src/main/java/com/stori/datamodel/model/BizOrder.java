package com.stori.datamodel.model;


import com.stori.datamodel.Money;
import com.stori.datamodel.OrderStatusEnum;
import org.aspectj.lang.annotation.Before;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "biz_order")
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
    private Money amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_time", updatable = false, nullable = false)
    private Date createTime;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="update_time", nullable = false)
    private Date updateTime;


    @Column(name = "order_status", nullable = false)
    private OrderStatusEnum orderStatus;

    public BizOrder() {
        this.createTime = new Date();
        this.updateTime = new Date();
        this.orderStatus = OrderStatusEnum.ACTIVE;
    }


    public void setAmount(Money amount) {
        this.updateTime = new Date();
        this.amount = amount;
    }

    public void setMerchant(Merchant merchant) {
        this.updateTime = new Date();
        this.merchant = merchant;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.updateTime = new Date();
        this.creditCard = creditCard;
    }

    public Long getId() {
        return id;
    }

    public void setOrderStatus(OrderStatusEnum orderStatus) {
        this.updateTime = new Date();
        this.orderStatus = orderStatus;
    }

    public OrderStatusEnum getOrderStatus() {
        return orderStatus;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public Money getAmount() {
        return amount;
    }
}
