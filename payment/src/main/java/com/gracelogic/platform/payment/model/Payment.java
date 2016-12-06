package com.gracelogic.platform.payment.model;


import com.gracelogic.platform.account.model.Account;
import com.gracelogic.platform.db.JPAProperties;
import com.gracelogic.platform.db.model.IdObject;
import com.gracelogic.platform.user.model.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Author: Igor Parkhomenko
 * Date: 11.12.14
 * Time: 14:30
 */
@Entity
@Table(name = JPAProperties.TABLE_PREFIX + "PAYMENT", schema = JPAProperties.DEFAULT_SCHEMA)
public class Payment extends IdObject<UUID> {
    @Id
    @Column(name = ID)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @Access(AccessType.PROPERTY)
    private UUID id;

    @Column(name = CREATED, nullable = false)
    private Date created;

    @Version
    @Column(name = CHANGED, nullable = false)
    private Date changed;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "PAYMENT_SYSTEM_ID", nullable = false)
    private PaymentSystem paymentSystem;

    @ManyToOne
    @JoinColumn(name = "PAYMENT_STATE_ID", nullable = false)
    private PaymentState paymentState;

    @Column(name = "PAYMENT_UID", nullable = true)
    private String paymentUID;

    @Column(name = "EXTERNAL_TYPE_UID", nullable = true)
    private String externalTypeUID;

    @Column(name = "REGISTERED_AMOUNT", nullable = false)
    private Long registeredAmount;

    @Column(name = "AMOUNT", nullable = false)
    private Long amount;

    @Column(name = "DESCRIPTION", nullable = true, length = 4000)
    private String description;

    @Column(name = "FEE", nullable = false)
    private Long fee;

    @Column(name = "TOTAL_AMOUNT", nullable = false)
    private Long totalAmount;

    @ManyToOne
    @JoinColumn(name = "EXECUTED_BY_USER_ID", nullable = true)
    private User executedByUser;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public Date getChanged() {
        return changed;
    }

    @Override
    public void setChanged(Date changed) {
        this.changed = changed;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public void setCreated(Date created) {
        this.created = created;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PaymentSystem getPaymentSystem() {
        return paymentSystem;
    }

    public void setPaymentSystem(PaymentSystem paymentSystem) {
        this.paymentSystem = paymentSystem;
    }

    public String getPaymentUID() {
        return paymentUID;
    }

    public void setPaymentUID(String paymentSystemUID) {
        this.paymentUID = paymentSystemUID;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PaymentState getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(PaymentState paymentState) {
        this.paymentState = paymentState;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getRegisteredAmount() {
        return registeredAmount;
    }

    public void setRegisteredAmount(Long registeredAmount) {
        this.registeredAmount = registeredAmount;
    }

    public String getExternalTypeUID() {
        return externalTypeUID;
    }

    public void setExternalTypeUID(String externalTypeUID) {
        this.externalTypeUID = externalTypeUID;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public User getExecutedByUser() {
        return executedByUser;
    }

    public void setExecutedByUser(User executedByUser) {
        this.executedByUser = executedByUser;
    }
}
