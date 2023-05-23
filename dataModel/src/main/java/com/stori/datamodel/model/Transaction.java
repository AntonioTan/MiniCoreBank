package com.stori.datamodel.model;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "TRANSACTION")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="credit_card_id", referencedColumnName = "id")
    private CreditCard creditCard;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="merchant_id", referencedColumnName = "id")
    private Merchant merchant;

    private int amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

}
