package com.stori.datamodel.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BILL")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date time;

    private int amount;

}
