package com.stori.datamodel.model;

import com.stori.bankuserservicefacade.CreditCardStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CREDIT_CARD")
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    private int creditLimit;

    private CreditCardStatus creditCardStatus;

    public void setCreditLimit(int creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void increaseCreditLimit(int creditLimitIncrease) {
        this.creditLimit += creditLimitIncrease;
    }

    public void decreaseCreditLimit(int creditLimitDecrease) {
        this.creditLimit -= creditLimitDecrease;
    }

    public void setCreditCardStatus(CreditCardStatus creditCardStatus) {
        this.creditCardStatus = creditCardStatus;
    }

}
