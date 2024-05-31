/*
 * This file is generated by jOOQ.
 */
package com.github.senocak.skj.jooq.tables.records;


import com.github.senocak.skj.jooq.tables.UserAudit;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserAuditRecord extends UpdatableRecordImpl<UserAuditRecord> implements Record7<Integer, Short, LocalDateTime, UUID, String, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.user_audit.rev</code>.
     */
    public void setRev(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.user_audit.rev</code>.
     */
    public Integer getRev() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.user_audit.revtype</code>.
     */
    public void setRevtype(Short value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.user_audit.revtype</code>.
     */
    public Short getRevtype() {
        return (Short) get(1);
    }

    /**
     * Setter for <code>public.user_audit.email_activated_at</code>.
     */
    public void setEmailActivatedAt(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.user_audit.email_activated_at</code>.
     */
    public LocalDateTime getEmailActivatedAt() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>public.user_audit.id</code>.
     */
    public void setId(UUID value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.user_audit.id</code>.
     */
    public UUID getId() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>public.user_audit.email</code>.
     */
    public void setEmail(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.user_audit.email</code>.
     */
    public String getEmail() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.user_audit.name</code>.
     */
    public void setName(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.user_audit.name</code>.
     */
    public String getName() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.user_audit.password</code>.
     */
    public void setPassword(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.user_audit.password</code>.
     */
    public String getPassword() {
        return (String) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<Integer, UUID> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row7<Integer, Short, LocalDateTime, UUID, String, String, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<Integer, Short, LocalDateTime, UUID, String, String, String> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return UserAudit.USER_AUDIT.REV;
    }

    @Override
    public Field<Short> field2() {
        return UserAudit.USER_AUDIT.REVTYPE;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return UserAudit.USER_AUDIT.EMAIL_ACTIVATED_AT;
    }

    @Override
    public Field<UUID> field4() {
        return UserAudit.USER_AUDIT.ID;
    }

    @Override
    public Field<String> field5() {
        return UserAudit.USER_AUDIT.EMAIL;
    }

    @Override
    public Field<String> field6() {
        return UserAudit.USER_AUDIT.NAME;
    }

    @Override
    public Field<String> field7() {
        return UserAudit.USER_AUDIT.PASSWORD;
    }

    @Override
    public Integer component1() {
        return getRev();
    }

    @Override
    public Short component2() {
        return getRevtype();
    }

    @Override
    public LocalDateTime component3() {
        return getEmailActivatedAt();
    }

    @Override
    public UUID component4() {
        return getId();
    }

    @Override
    public String component5() {
        return getEmail();
    }

    @Override
    public String component6() {
        return getName();
    }

    @Override
    public String component7() {
        return getPassword();
    }

    @Override
    public Integer value1() {
        return getRev();
    }

    @Override
    public Short value2() {
        return getRevtype();
    }

    @Override
    public LocalDateTime value3() {
        return getEmailActivatedAt();
    }

    @Override
    public UUID value4() {
        return getId();
    }

    @Override
    public String value5() {
        return getEmail();
    }

    @Override
    public String value6() {
        return getName();
    }

    @Override
    public String value7() {
        return getPassword();
    }

    @Override
    public UserAuditRecord value1(Integer value) {
        setRev(value);
        return this;
    }

    @Override
    public UserAuditRecord value2(Short value) {
        setRevtype(value);
        return this;
    }

    @Override
    public UserAuditRecord value3(LocalDateTime value) {
        setEmailActivatedAt(value);
        return this;
    }

    @Override
    public UserAuditRecord value4(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public UserAuditRecord value5(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public UserAuditRecord value6(String value) {
        setName(value);
        return this;
    }

    @Override
    public UserAuditRecord value7(String value) {
        setPassword(value);
        return this;
    }

    @Override
    public UserAuditRecord values(Integer value1, Short value2, LocalDateTime value3, UUID value4, String value5, String value6, String value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserAuditRecord
     */
    public UserAuditRecord() {
        super(UserAudit.USER_AUDIT);
    }

    /**
     * Create a detached, initialised UserAuditRecord
     */
    public UserAuditRecord(Integer rev, Short revtype, LocalDateTime emailActivatedAt, UUID id, String email, String name, String password) {
        super(UserAudit.USER_AUDIT);

        setRev(rev);
        setRevtype(revtype);
        setEmailActivatedAt(emailActivatedAt);
        setId(id);
        setEmail(email);
        setName(name);
        setPassword(password);
        resetChangedOnNotNull();
    }
}