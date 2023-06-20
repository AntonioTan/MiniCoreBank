package com.stori.recordfacade;

import com.stori.datamodel.model.CancelOrderRecord;
import com.stori.datamodel.model.CreateOrderRecord;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

public interface OrderRecordService {

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    void addCreateOrderRecord(CreateOrderRecord createOrderRecord);

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    void addCancelOrderRecord(CancelOrderRecord cancelOrderRecord);
}
