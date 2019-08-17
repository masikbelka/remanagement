package com.masik.remanagement.repository;

import com.masik.remanagement.domain.ContractDocumentEntryRow;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContractDocumentEntryRow entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContractDocumentEntryRowRepository extends JpaRepository<ContractDocumentEntryRow, Long> {

}
