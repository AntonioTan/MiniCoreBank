package com.stori.recordfacade;

import com.stori.datamodel.model.CreditReleasedRecord;
import com.stori.datamodel.model.CreditUsedRecord;

public interface CreditRecordServiceBase {

    /**
     * @param creditUsedRecord the new credit used record
     */
    public abstract void addCreditUsedRecord(CreditUsedRecord creditUsedRecord);

    /**
     * @param creditReleaseRecord the credit release record
     */
    public abstract void addCreditReleaseRecord(CreditReleasedRecord creditReleaseRecord);
}
