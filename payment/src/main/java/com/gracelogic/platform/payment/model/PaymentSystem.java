package com.gracelogic.platform.payment.model;

import com.gracelogic.platform.db.JPAProperties;
import com.gracelogic.platform.db.model.IdObject;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Author: Igor Parkhomenko
 * Date: 11.12.14
 * Time: 13:17
 */
@Entity
@Table(name = JPAProperties.TABLE_PREFIX + "PAYMENT_SYSTEM", schema = JPAProperties.DEFAULT_SCHEMA)
public class PaymentSystem extends IdObject<UUID> {
    @Id
    @Column(name = ID)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @org.hibernate.annotations.Type(type = "pg-uuid")
    private UUID id;

    @Column(name = CREATED, nullable = false)
    private Date created;

    @Version
    @Column(name = CHANGED, nullable = false)
    private Date changed;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", nullable = true, length = 4000)
    private String description;

    @Column(name = "SECURITY_KEY", nullable = true)
    private String securityKey;

    @Column(name = "ALLOWED_ADDRESSES", nullable = true)
    private String allowedAddresses;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "FEE", nullable = false)
    private Long fee;

    @Column(name = "IS_FEE_INCLUDED", nullable = false)
    private Boolean feeIncluded;

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public Date getChanged() {
        return changed;
    }

    @Override
    public void setChanged(Date changed) {
        this.changed = changed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public Boolean getFeeIncluded() {
        return feeIncluded;
    }

    public void setFeeIncluded(Boolean feeIncluded) {
        this.feeIncluded = feeIncluded;
    }

    public String getAllowedAddresses() {
        return allowedAddresses;
    }

    public void setAllowedAddresses(String allowedAddresses) {
        this.allowedAddresses = allowedAddresses;
    }
}