package com.masik.remanagement.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A TenantContract.
 */
@Entity
@Table(name = "tenant_contract")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TenantContract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "effective_date")
    private ZonedDateTime effectiveDate;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @Column(name = "rent")
    private Double rent;

    @Column(name = "deposit")
    private Double deposit;

    @Column(name = "utilities")
    private String utilities;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tenant_contract_free_period",
               joinColumns = @JoinColumn(name = "tenant_contract_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "free_period_id", referencedColumnName = "id"))
    private Set<FreePeriod> freePeriods = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tenant_contract_properties",
               joinColumns = @JoinColumn(name = "tenant_contract_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "properties_id", referencedColumnName = "id"))
    private Set<Property> properties = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tenant_contract_tenants",
               joinColumns = @JoinColumn(name = "tenant_contract_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tenants_id", referencedColumnName = "id"))
    private Set<Tenant> tenants = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("tenantContracts")
    private ContractDocument contractDocument;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public TenantContract code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ZonedDateTime getEffectiveDate() {
        return effectiveDate;
    }

    public TenantContract effectiveDate(ZonedDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
        return this;
    }

    public void setEffectiveDate(ZonedDateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public TenantContract startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public TenantContract endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Double getRent() {
        return rent;
    }

    public TenantContract rent(Double rent) {
        this.rent = rent;
        return this;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }

    public Double getDeposit() {
        return deposit;
    }

    public TenantContract deposit(Double deposit) {
        this.deposit = deposit;
        return this;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public String getUtilities() {
        return utilities;
    }

    public TenantContract utilities(String utilities) {
        this.utilities = utilities;
        return this;
    }

    public void setUtilities(String utilities) {
        this.utilities = utilities;
    }

    public Set<FreePeriod> getFreePeriods() {
        return freePeriods;
    }

    public TenantContract freePeriods(Set<FreePeriod> freePeriods) {
        this.freePeriods = freePeriods;
        return this;
    }

    public TenantContract addFreePeriod(FreePeriod freePeriod) {
        this.freePeriods.add(freePeriod);
        freePeriod.getContracts().add(this);
        return this;
    }

    public TenantContract removeFreePeriod(FreePeriod freePeriod) {
        this.freePeriods.remove(freePeriod);
        freePeriod.getContracts().remove(this);
        return this;
    }

    public void setFreePeriods(Set<FreePeriod> freePeriods) {
        this.freePeriods = freePeriods;
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public TenantContract properties(Set<Property> properties) {
        this.properties = properties;
        return this;
    }

    public TenantContract addProperties(Property property) {
        this.properties.add(property);
        property.getContracts().add(this);
        return this;
    }

    public TenantContract removeProperties(Property property) {
        this.properties.remove(property);
        property.getContracts().remove(this);
        return this;
    }

    public void setProperties(Set<Property> properties) {
        this.properties = properties;
    }

    public Set<Tenant> getTenants() {
        return tenants;
    }

    public TenantContract tenants(Set<Tenant> tenants) {
        this.tenants = tenants;
        return this;
    }

    public TenantContract addTenants(Tenant tenant) {
        this.tenants.add(tenant);
        tenant.getContracts().add(this);
        return this;
    }

    public TenantContract removeTenants(Tenant tenant) {
        this.tenants.remove(tenant);
        tenant.getContracts().remove(this);
        return this;
    }

    public void setTenants(Set<Tenant> tenants) {
        this.tenants = tenants;
    }

    public ContractDocument getContractDocument() {
        return contractDocument;
    }

    public TenantContract contractDocument(ContractDocument contractDocument) {
        this.contractDocument = contractDocument;
        return this;
    }

    public void setContractDocument(ContractDocument contractDocument) {
        this.contractDocument = contractDocument;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TenantContract)) {
            return false;
        }
        return id != null && id.equals(((TenantContract) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TenantContract{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", effectiveDate='" + getEffectiveDate() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", rent=" + getRent() +
            ", deposit=" + getDeposit() +
            ", utilities='" + getUtilities() + "'" +
            "}";
    }
}
