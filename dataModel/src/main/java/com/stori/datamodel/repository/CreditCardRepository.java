package com.stori.datamodel.repository;

import com.stori.datamodel.CreditCardStatusEnum;
import com.stori.datamodel.Money;
import com.stori.datamodel.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    //    @Lock(value= LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select id, user_id, start_date, end_date, update_time, credit_limit_number, credit_used_number, credit_card_status from credit_card c where c.id=:creditCardId for update", nativeQuery = true)
    CreditCard selectCreditCardForUpdate(@Param("creditCardId") Long id);

    @Modifying(clearAutomatically = true)
    @Query("update CreditCard set creditLimit.number=:creditLimit where id=:creditCardId and creditCardStatus=:#{#creditCardStatus}")
    int setCreditLimit(@Param("creditCardId") Long id, @Param("creditLimit") long creditLimit, @Param("creditCardStatus") CreditCardStatusEnum creditCardStatus);

    @Modifying(clearAutomatically = true)
    @Query("update CreditCard set creditUsed.number=:#{#creditUsed.number} where id=:creditCardId and creditUsed.number + :#{#creditUsed.number}<=creditLimit.number and creditCardStatus=:#{#creditCardStatus}")
    int updateCreditUsed(@Param("creditCardId") Long creditCardId, @Param("creditUsed") Money creditUsed, @Param("creditCardStatus") CreditCardStatusEnum creditCardStatus);

    @Modifying(clearAutomatically = true)
    @Query("update CreditCard set creditCardStatus=:#{#creditCardStatus} where id=:creditCardId")
    int setCreditCardStatus(@Param("creditCardId") Long id, @Param("creditCardStatus") CreditCardStatusEnum creditCardStatus);

}
