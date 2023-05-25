package com.stori.datamodel.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "TRANSACTION")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(unique = true, nullable = false, updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name="credit_card_id", referencedColumnName = "id")
    private CreditCard creditCard;

    @OneToOne
    @JoinColumn(name="merchant_id", referencedColumnName = "id")
    private Merchant merchant;

    @Column(updatable = false, nullable = false)
    private int amount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false, nullable = false)
    private Date timestamp;

}
