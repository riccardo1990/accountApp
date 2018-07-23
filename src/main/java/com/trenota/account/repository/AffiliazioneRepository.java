package com.trenota.account.repository;

import com.trenota.account.domain.Affiliazione;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Affiliazione entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AffiliazioneRepository extends JpaRepository<Affiliazione, Long> {

}
