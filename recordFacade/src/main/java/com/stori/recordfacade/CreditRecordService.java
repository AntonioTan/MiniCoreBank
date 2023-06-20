package com.stori.recordfacade;

import com.stori.datamodel.model.CreditReleasedRecord;
import com.stori.datamodel.model.CreditUsedRecord;

public interface CreditRecordService {

    /**
     */
    public abstract void addCreditUsedRecord(CreditUsedRecord creditUsedRecord);

    /**
     */
    public abstract void addCreditReleaseRecord(CreditReleasedRecord creditReleasedRecord);
}
