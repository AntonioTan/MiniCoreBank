package com.stori.recordservice;

import com.stori.datamodel.model.Record;
import com.stori.datamodel.repository.RecordRepository;
import com.stori.recordfacade.RecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecordServiceImpl<T extends Record> implements RecordService<T> {
    private static final Logger logger = LoggerFactory.getLogger(RecordServiceImpl.class);
    private RecordRepository<T> recordRepository;

    @Override
    public void saveRecord(T r) {
        recordRepository.save(r);
    }

    @Override
    public Integer findByRequestId(Long requestId) {
        return recordRepository.findByRequestId(requestId);
    }
}
