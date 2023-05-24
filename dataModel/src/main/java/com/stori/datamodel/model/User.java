package com.stori.datamodel.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(unique = true, nullable = false, updatable = false)
    private Long id;

    private String name;

    private int balance;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "credit_card_id", referencedColumnName = "id", nullable = false)
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
        this.creditCard = creditCard;
    }



}
