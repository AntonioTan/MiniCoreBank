package com.stori.datamodel.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CancelOrderRecord extends Record {
    public static final String content = "Cancel Order Record";

    @Column(name = "order_id", updatable = false, insertable = false)
    private Long orderId;

    public CancelOrderRecord() {
        super();
    }

    public CancelOrderRecord(Long requestId) {
        super(content, requestId);
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
