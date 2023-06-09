package com.stori.recordfacade;

public interface OrderRecordServiceBase {
    void addCreateOrderRecord(Long orderId);

    void addCancelOrderRecord(Long orderId);
}
