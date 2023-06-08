package com.stori.recordfacade;

import com.stori.datamodel.model.CreditReleasedRecord;
import com.stori.datamodel.model.CreditUsedRecord;

public interface CreditRecordServiceBase {

    /**
     * @param creditCardId credit card id
     * @param creditUsed credit to use
     */
    public abstract void addCreditUsedRecord(Long creditCardId, int creditUsed);

    /**
     * @param creditCardId credit card id
     * @param creditReleased credit to release
     */
    public abstract void addCreditReleaseRecord(Long creditCardId, int creditReleased);
}
