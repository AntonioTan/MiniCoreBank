package com.stori.recordfacade;

import com.stori.datamodel.model.CancelOrderRecord;
import com.stori.datamodel.model.CreateOrderRecord;

/**
 * The interface defines record service to save record for operations related to order service
 */
public interface OrderRecordService {

    void addCreateOrderRecord(CreateOrderRecord createOrderRecord);

    void addCancelOrderRecord(CancelOrderRecord cancelOrderRecord);
}
