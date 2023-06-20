package com.stori.datamodel.model;


import org.springframework.data.jpa.repository.Lock;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class CreditUsedRecord extends Record implements Serializable {
    private static final String content = "Credit Used Record";
    public CreditUsedRecord() {
        super(content, new Date());
    }
    public CreditUsedRecord(CreditCard creditCard, int creditUsed, long requestId) {
        super(content, new Date());
        this.creditCard = creditCard;
        this.creditUsed = creditUsed;
        this.requestId = requestId;
    }

    @OneToOne()
    @JoinColumn(name = "credit_card_id", referencedColumnName = "id", updatable = false, nullable = false)
    private CreditCard creditCard;

    @Column(name = "credit_used", nullable = false, updatable = false)
    private int creditUsed;


}
