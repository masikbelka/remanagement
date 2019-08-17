package com.masik.remanagement.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.masik.remanagement.domain.enumeration.UnitType;

import com.masik.remanagement.domain.enumeration.Furnishing;

/**
 * A Property.
 */
@Entity
@Table(name = "property")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "number")
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private UnitType type;

    @Column(name = "bedrooms")
    private Integer bedrooms;

    @Enumerated(EnumType.STRING)
    @Column(name = "furnishing")
    private Furnishing furnishing;

    @Column(name = "electricity")
    private Integer electricity;

    @Column(name = "water")
    private Integer water;

    @Column(name = "parking")
    private Integer parking;

    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    @ManyToMany(mappedBy = "properties")
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

    public String getCode() {
        return code;
    }

    public Property code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Property name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public Property number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public UnitType getType() {
        return type;
    }

    public Property type(UnitType type) {
        this.type = type;
        return this;
    }

    public void setType(UnitType type) {
        this.type = type;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public Property bedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
        return this;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Furnishing getFurnishing() {
        return furnishing;
    }

    public Property furnishing(Furnishing furnishing) {
        this.furnishing = furnishing;
        return this;
    }

    public void setFurnishing(Furnishing furnishing) {
        this.furnishing = furnishing;
    }

    public Integer getElectricity() {
        return electricity;
    }

    public Property electricity(Integer electricity) {
        this.electricity = electricity;
        return this;
    }

    public void setElectricity(Integer electricity) {
        this.electricity = electricity;
    }

    public Integer getWater() {
        return water;
    }

    public Property water(Integer water) {
        this.water = water;
        return this;
    }

    public void setWater(Integer water) {
        this.water = water;
    }

    public Integer getParking() {
        return parking;
    }

    public Property parking(Integer parking) {
        this.parking = parking;
        return this;
    }

    public void setParking(Integer parking) {
        this.parking = parking;
    }

    public Location getLocation() {
        return location;
    }

    public Property location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<TenantContract> getContracts() {
        return contracts;
    }

    public Property contracts(Set<TenantContract> tenantContracts) {
        this.contracts = tenantContracts;
        return this;
    }

    public Property addContract(TenantContract tenantContract) {
        this.contracts.add(tenantContract);
        tenantContract.getProperties().add(this);
        return this;
    }

    public Property removeContract(TenantContract tenantContract) {
        this.contracts.remove(tenantContract);
        tenantContract.getProperties().remove(this);
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
        if (!(o instanceof Property)) {
            return false;
        }
        return id != null && id.equals(((Property) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Property{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", number='" + getNumber() + "'" +
            ", type='" + getType() + "'" +
            ", bedrooms=" + getBedrooms() +
            ", furnishing='" + getFurnishing() + "'" +
            ", electricity=" + getElectricity() +
            ", water=" + getWater() +
            ", parking=" + getParking() +
            "}";
    }
}
