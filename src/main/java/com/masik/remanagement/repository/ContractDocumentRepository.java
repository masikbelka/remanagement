package com.masik.remanagement.repository;

import com.masik.remanagement.domain.ContractDocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContractDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContractDocumentRepository extends JpaRepository<ContractDocument, Long> {

}
