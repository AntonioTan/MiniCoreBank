package com.stori.recordfacade;

import com.stori.datamodel.model.CreditReleasedRecord;
import com.stori.datamodel.model.CreditUsedRecord;

public interface CreditRecordService {

    /**
     */
    void addCreditUsedRecord(CreditUsedRecord creditUsedRecord);

    /**
     */
    void addCreditReleaseRecord(CreditReleasedRecord creditReleasedRecord);
}
