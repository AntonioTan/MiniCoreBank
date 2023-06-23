package com.stori.datamodel.model;

import com.stori.datamodel.CreditCardStatusEnum;
import com.stori.datamodel.Money;
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
    @Embedded
    @AttributeOverride(name = "number", column=@Column(name="credit_limit_number"))
    private Money creditLimit;

    @Column(nullable = false)
    @Embedded
    @AttributeOverride(name = "number", column=@Column(name="credit_used_number"))
    private Money creditUsed;

    @Column(name = "credit_card_status", nullable = false)
    private CreditCardStatusEnum creditCardStatus;

    public CreditCard() {
        this.startDate = new Date();
        cal.setTime(this.startDate);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + CREDIT_CARD_VALID_PERIOD);
        this.endDate = cal.getTime();
        this.creditCardStatus = CreditCardStatusEnum.INIT;
        this.creditLimit = new Money(0L);
        this.creditUsed = new Money(0L);
    }

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void setCreditLimit(Money creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void increaseCreditLimit(int creditLimitIncrease) {
        this.creditLimit.setNumber(this.creditLimit.getNumber() + creditLimitIncrease);
    }

    public void decreaseCreditLimit(int creditLimitDecrease) {
        this.creditLimit.setNumber(this.creditLimit.getNumber() -  creditLimitDecrease);
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void setCreditCardStatus(CreditCardStatusEnum creditCardStatus) {
        this.creditCardStatus = creditCardStatus;
    }

//    @Lock(LockModeType.PESSIMISTIC_READ)
    public Money getCreditLimit() {
        return this.creditLimit;
    }

    public long getId() {
        return this.id;
    }

//    @Lock(LockModeType.PESSIMISTIC_READ)
    public Money getCreditUsed() {
        return this.creditUsed;
    }

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void setCreditUsed(Money creditUsed) {
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
