package com.stori.datamodel.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
public class CreateOrderRecord extends Record{

    private static final String content = "Order Creation Record";

    @OneToOne
    @JoinColumn(name="order_id", referencedColumnName = "id", updatable = false)
    private BizOrder bizOrder;
    public CreateOrderRecord(Long requestId) {
        super(content, new Date(), requestId);
    }

    public CreateOrderRecord() {

    }


    public void setOrder(BizOrder bizOrder) {
        this.bizOrder = bizOrder;
    }
}
