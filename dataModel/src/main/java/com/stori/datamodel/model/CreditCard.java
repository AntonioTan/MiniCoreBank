package com.stori.datamodel.model;

import com.stori.datamodel.CreditCardStatusEnum;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "credit_card")
public class CreditCard {
    private final static Calendar cal = Calendar.getInstance();
    private final static int CREDIT_CARD_VALID_PERIOD = 10;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private int creditLimit;

    @Column(nullable = false)
    private int creditUsed;

    @Column(name = "credit_card_status", nullable = false)
    private CreditCardStatusEnum creditCardStatus;

    public CreditCard() {
        this.startDate = new Date();
        cal.setTime(this.startDate);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + CREDIT_CARD_VALID_PERIOD);
        this.endDate = cal.getTime();
        this.creditCardStatus = CreditCardStatusEnum.INIT;
        this.creditLimit = 0;
        this.creditUsed = 0;
    }

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void setCreditLimit(int creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void increaseCreditLimit(int creditLimitIncrease) {
        this.creditLimit += creditLimitIncrease;
    }

    public void decreaseCreditLimit(int creditLimitDecrease) {
        this.creditLimit -= creditLimitDecrease;
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void setCreditCardStatus(CreditCardStatusEnum creditCardStatus) {
        this.creditCardStatus = creditCardStatus;
    }

//    @Lock(LockModeType.PESSIMISTIC_READ)
    public int getCreditLimit() {
        return this.creditLimit;
    }

    public long getId() {
        return this.id;
    }

//    @Lock(LockModeType.PESSIMISTIC_READ)
    public int getCreditUsed() {
        return this.creditUsed;
    }

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void setCreditUsed(int creditUsed) {
        this.creditUsed = creditUsed;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    @Lock(LockModeType.PESSIMISTIC_READ)

    public CreditCardStatusEnum getCreditCardStatus() {
        return this.creditCardStatus;
    }


}
