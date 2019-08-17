package com.masik.remanagement.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Tenant.
 */
@Entity
@Table(name = "tenant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tenant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "qid")
    private String qid;

    @Column(name = "name")
    private String name;

    @Column(name = "arabic_name")
    private String arabicName;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "fax")
    private String fax;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "email")
    private String email;

    @Column(name = "pobox")
    private String pobox;

    @ManyToMany(mappedBy = "tenants")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<TenantContract> contracts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQid() {
        return qid;
    }

    public Tenant qid(String qid) {
        this.qid = qid;
        return this;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getName() {
        return name;
    }

    public Tenant name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArabicName() {
        return arabicName;
    }

    public Tenant arabicName(String arabicName) {
        this.arabicName = arabicName;
        return this;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }

    public String getTelephone() {
        return telephone;
    }

    public Tenant telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public Tenant fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMobile() {
        return mobile;
    }

    public Tenant mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public Tenant email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPobox() {
        return pobox;
    }

    public Tenant pobox(String pobox) {
        this.pobox = pobox;
        return this;
    }

    public void setPobox(String pobox) {
        this.pobox = pobox;
    }

    public Set<TenantContract> getContracts() {
        return contracts;
    }

    public Tenant contracts(Set<TenantContract> tenantContracts) {
        this.contracts = tenantContracts;
        return this;
    }

    public Tenant addContract(TenantContract tenantContract) {
        this.contracts.add(tenantContract);
        tenantContract.getTenants().add(this);
        return this;
    }

    public Tenant removeContract(TenantContract tenantContract) {
        this.contracts.remove(tenantContract);
        tenantContract.getTenants().remove(this);
        return this;
    }

    public void setContracts(Set<TenantContract> tenantContracts) {
        this.contracts = tenantContracts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tenant)) {
            return false;
        }
        return id != null && id.equals(((Tenant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tenant{" +
            "id=" + getId() +
            ", qid='" + getQid() + "'" +
            ", name='" + getName() + "'" +
            ", arabicName='" + getArabicName() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", fax='" + getFax() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", email='" + getEmail() + "'" +
            ", pobox='" + getPobox() + "'" +
            "}";
    }
}
