package com.masik.remanagement.repository;

import com.masik.remanagement.domain.FreePeriod;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FreePeriod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FreePeriodRepository extends JpaRepository<FreePeriod, Long> {

}
