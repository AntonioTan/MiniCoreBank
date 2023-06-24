package com.stori.datamodel.repository;

import com.stori.datamodel.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.annotation.Resource;

//@NoRepositoryBean
@NoRepositoryBean
public interface RecordCommonRepository<T extends Record> extends JpaRepository<T, Long> {
    @Query(value="select count(*) from #{#entityName} t where t.requestId=:requestId")
    int findByRequestId(@Param(value = "requestId") long requestId);
}
