package com.masik.remanagement.repository;

import com.masik.remanagement.domain.TenantContract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the TenantContract entity.
 */
@Repository
public interface TenantContractRepository extends JpaRepository<TenantContract, Long> {

    @Query(value = "select distinct tenantContract from TenantContract tenantContract left join fetch tenantContract.freePeriods left join fetch tenantContract.properties left join fetch tenantContract.tenants",
        countQuery = "select count(distinct tenantContract) from TenantContract tenantContract")
    Page<TenantContract> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct tenantContract from TenantContract tenantContract left join fetch tenantContract.freePeriods left join fetch tenantContract.properties left join fetch tenantContract.tenants")
    List<TenantContract> findAllWithEagerRelationships();

    @Query("select tenantContract from TenantContract tenantContract left join fetch tenantContract.freePeriods left join fetch tenantContract.properties left join fetch tenantContract.tenants where tenantContract.id =:id")
    Optional<TenantContract> findOneWithEagerRelationships(@Param("id") Long id);

}
