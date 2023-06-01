package com.stori.datamodel.model;

import com.stori.bankuserservicefacade.CreditCardStatus;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "CREDIT_CARD")
public class CreditCard {
    private final static Calendar cal = Calendar.getInstance();
    private final static int CREDIT_CARD_VALID_PERIOD = 10;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    private int creditLimit;

    @Column(name="credit_card_status", nullable = false)
    private CreditCardStatus creditCardStatus;

    public CreditCard() {
        this.startDate = new Date();
        cal.setTime(this.startDate);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)+CREDIT_CARD_VALID_PERIOD);
        this.endDate = cal.getTime();
        this.creditCardStatus = CreditCardStatus.INIT;
    }

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

    public int getCreditLimit() {
        return this.creditLimit;
    }

    public long getId() {
        return this.id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CreditCardStatus getCreditCardStatus() {
        return this.creditCardStatus;
    }


}
