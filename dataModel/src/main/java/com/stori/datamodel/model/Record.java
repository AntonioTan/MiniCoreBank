package com.stori.datamodel.model;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class Record extends BasicObj{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    public Long id;

    @Column(nullable = false)
    public String content;

    @Column(name = "requestId", unique = true, nullable = false, updatable = false)
    public Long requestId;

//    @Column(name = "request_id", unique = true, nullable = false, updatable = false)
//    private long requestId;
    public Record(){
        super();
    };

    public Record(String content) {
        super();
        this.content = content;
    }
    public Record(String content, Long requestId) {
        super();
        this.content = content;
        this.requestId = requestId;
    }
}
