package com.stori.datamodel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
public class CreditReleasedRecord extends Record{
    private static final String content = "Credit Released Record";
    @OneToOne()
    @JoinColumn(name = "credit_card_id", referencedColumnName = "id", updatable = false)
    private CreditCard creditCard;

    @Column(name = "credit_released", nullable = false, updatable = false)
    private int creditReleased;

    public CreditReleasedRecord(CreditCard creditCard, int creditReleased) {
        super(content, new Date());
        this.creditCard = creditCard;
        this.creditReleased = creditReleased;
    }

}
