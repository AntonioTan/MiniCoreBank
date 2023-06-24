package com.stori.recordfacade;

import com.stori.datamodel.model.Record;

public interface RecordService<T extends Record> {
    void saveRecord(T r);
    Integer findByRequestId(Long requestId);
}
