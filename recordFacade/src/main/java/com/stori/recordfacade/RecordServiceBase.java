package com.stori.recordfacade;

import com.stori.datamodel.model.Record;

public interface RecordServiceBase<T extends Record> {
    void addRecord(T r);
    int findByRequestId(long requestId);
}
