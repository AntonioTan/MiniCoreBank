package com.stori.datamodel.model;

import com.stori.datamodel.Money;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class CreditReleasedRecord extends Record{
    private static final String content = "Credit Released Record";
    @OneToOne()
    @JoinColumn(name = "credit_card_id", referencedColumnName = "id", updatable = false)
    private CreditCard creditCard;

    @Column(name = "credit_released", nullable = false, updatable = false)
    private Money creditReleased;

    public CreditReleasedRecord() {
        super();
    }

    public CreditReleasedRecord(CreditCard creditCard, Money creditReleased, long requestId) {
        super(content, requestId);
        this.creditCard = creditCard;
        this.creditReleased = creditReleased;
    }

}
