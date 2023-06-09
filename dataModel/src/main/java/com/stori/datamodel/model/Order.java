package com.stori.datamodel.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Order")
public class Order {
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

    public Order() {
        this.timestamp = new Date();
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
}
