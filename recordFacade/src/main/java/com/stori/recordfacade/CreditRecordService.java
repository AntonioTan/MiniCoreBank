package com.stori.recordfacade;

import com.stori.datamodel.model.CreditReleasedRecord;
import com.stori.datamodel.model.CreditUsedRecord;

/**
 * The interface defines record service to save record for operations related to credit service
 */
public interface CreditRecordService {

    /**
     */
    void addCreditUsedRecord(CreditUsedRecord creditUsedRecord);

    /**
     */
    void addCreditReleaseRecord(CreditReleasedRecord creditReleasedRecord);
}
