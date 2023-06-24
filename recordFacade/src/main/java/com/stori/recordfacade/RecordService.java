package com.stori.recordfacade;

import com.stori.datamodel.model.Record;

/**
 * @param <T> generic type that extends Record class
 *            The interface defines basic behavior of record service interface
 */
public interface RecordService<T extends Record> {
    void saveRecord(T r);

    Integer findByRequestId(Long requestId);
}
