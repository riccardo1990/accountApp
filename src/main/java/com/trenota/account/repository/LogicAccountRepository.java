package com.trenota.account.repository;

import com.trenota.account.domain.LogicAccount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the LogicAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogicAccountRepository extends JpaRepository<LogicAccount, Long> {

}
