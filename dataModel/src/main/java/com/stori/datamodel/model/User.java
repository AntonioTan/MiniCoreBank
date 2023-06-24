package com.stori.datamodel.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="user")
public class User extends BasicObj{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name="name", nullable = false, updatable = false)
    private String name;

    @Column(nullable = false)
    private int balance;

    public User(){
        super();
    };

    public User(String name) {
        super();
        this.name = name;
        this.balance = 0;
    }

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "credit_card_id", referencedColumnName = "id")
    private CreditCard creditCard;


    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getBalance() {
        return this.balance;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.updateTime = new Date();
        this.creditCard = creditCard;
    }

    public CreditCard getCreditCard() {
        return this.creditCard;
    }



}
