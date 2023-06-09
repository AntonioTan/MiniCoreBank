package com.stori.datamodel.model;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

public class CancelOrderRecord extends Record{
    public static final String content = "Cancel Order Record";
    public CancelOrderRecord() {
        super(content, new Date());
    }

    @Column(name = "order_id", updatable = false, insertable = false)
    private Long orderId;

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
