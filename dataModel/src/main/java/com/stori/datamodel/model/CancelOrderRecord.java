package com.stori.datamodel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
public class CancelOrderRecord extends Record{
    public static final String content = "Cancel Order Record";
    public CancelOrderRecord(Long requestId) {
        super(content, new Date(), requestId);
    }

    @Column(name = "order_id", updatable = false, insertable = false)
    private Long orderId;

    public CancelOrderRecord() {

    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
