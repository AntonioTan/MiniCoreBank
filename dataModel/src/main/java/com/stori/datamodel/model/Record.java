package com.stori.datamodel.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    public Long id;

    @Column(nullable = false)
    public String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp", nullable = false)
    private Date timestamp;

    public Record(String content, Date timestamp) {
        this.content = content;
        this.timestamp = timestamp;
    }
}
