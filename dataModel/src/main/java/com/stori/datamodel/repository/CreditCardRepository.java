package com.stori.datamodel.repository;

import com.stori.datamodel.CreditCardStatus;
import com.stori.datamodel.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
//    @Lock(value= LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select * from Credit_Card c where c.id=:creditCardId for update", nativeQuery = true)
    public CreditCard selectCreditCardForUpdate(@Param("creditCardId") Long id);

    @Modifying(clearAutomatically = true)
    @Query("update CreditCard set creditLimit=:creditLimit where id=:creditCardId and creditCardStatus=:#{#creditCardStatus}")
    public int setCreditLimit(@Param("creditCardId") Long id, @Param("creditLimit") int creditLimit, @Param("creditCardStatus") CreditCardStatus creditCardStatus);

    @Modifying(clearAutomatically = true)
    @Query("update CreditCard set creditUsed=creditUsed+:creditUsed where id=:creditCardId and creditUsed+:creditUsed<=creditLimit and creditCardStatus=:#{#creditCardStatus}")
    public int addCreditUsed(@Param("creditCardId") Long creditCardId, @Param("creditUsed") int creditUsed, @Param("creditCardStatus") CreditCardStatus creditCardStatus);

    @Modifying(clearAutomatically = true)
    @Query("update CreditCard set creditCardStatus=:#{#creditCardStatus} where id=:creditCardId")
    public int setCreditCardStatus(@Param("creditCardId") Long id, @Param("creditCardStatus")CreditCardStatus creditCardStatus);

}
