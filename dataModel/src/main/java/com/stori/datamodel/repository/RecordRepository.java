package com.stori.datamodel.repository;

import com.stori.datamodel.model.Record;
import org.springframework.data.repository.NoRepositoryBean;

import javax.annotation.Resource;

@NoRepositoryBean
public interface RecordRepository<T extends Record> extends RecordCommonRepository<T>{
}
