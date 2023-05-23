package com.stori.datamodel.model;

import javax.persistence.*;

@Entity
@Table(name="MERCHANT")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
}
