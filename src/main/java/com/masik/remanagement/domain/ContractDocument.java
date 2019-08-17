package com.masik.remanagement.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.masik.remanagement.domain.enumeration.ContractDocumentType;

/**
 * A ContractDocument.
 */
@Entity
@Table(name = "contract_document")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContractDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Column(name = "code", unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ContractDocumentType type;

    @Column(name = "file_name")
    private String fileName;

    @OneToMany(mappedBy = "contractDocument")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TenantContract> tenantContacts = new HashSet<>();

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

    public ContractDocument code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ContractDocumentType getType() {
        return type;
    }

    public ContractDocument type(ContractDocumentType type) {
        this.type = type;
        return this;
    }

    public void setType(ContractDocumentType type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public ContractDocument fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Set<TenantContract> getTenantContacts() {
        return tenantContacts;
    }

    public ContractDocument tenantContacts(Set<TenantContract> tenantContracts) {
        this.tenantContacts = tenantContracts;
        return this;
    }

    public ContractDocument addTenantContact(TenantContract tenantContract) {
        this.tenantContacts.add(tenantContract);
        tenantContract.setContractDocument(this);
        return this;
    }

    public ContractDocument removeTenantContact(TenantContract tenantContract) {
        this.tenantContacts.remove(tenantContract);
        tenantContract.setContractDocument(null);
        return this;
    }

    public void setTenantContacts(Set<TenantContract> tenantContracts) {
        this.tenantContacts = tenantContracts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContractDocument)) {
            return false;
        }
        return id != null && id.equals(((ContractDocument) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ContractDocument{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", type='" + getType() + "'" +
            ", fileName='" + getFileName() + "'" +
            "}";
    }
}
