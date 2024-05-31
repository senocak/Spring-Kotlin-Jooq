/*
 * This file is generated by jOOQ.
 */
package com.github.senocak.skj.jooq.tables.records;


import com.github.senocak.skj.jooq.tables.EmailActivationTokensAud;

import java.time.LocalDateTime;
import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class EmailActivationTokensAudRecord extends UpdatableRecordImpl<EmailActivationTokensAudRecord> implements Record6<Integer, Short, LocalDateTime, UUID, UUID, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.email_activation_tokens_aud.rev</code>.
     */
    public void setRev(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.email_activation_tokens_aud.rev</code>.
     */
    public Integer getRev() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.email_activation_tokens_aud.revtype</code>.
     */
    public void setRevtype(Short value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.email_activation_tokens_aud.revtype</code>.
     */
    public Short getRevtype() {
        return (Short) get(1);
    }

    /**
     * Setter for
     * <code>public.email_activation_tokens_aud.expiration_date</code>.
     */
    public void setExpirationDate(LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for
     * <code>public.email_activation_tokens_aud.expiration_date</code>.
     */
    public LocalDateTime getExpirationDate() {
        return (LocalDateTime) get(2);
    }

    /**
     * Setter for <code>public.email_activation_tokens_aud.id</code>.
     */
    public void setId(UUID value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.email_activation_tokens_aud.id</code>.
     */
    public UUID getId() {
        return (UUID) get(3);
    }

    /**
     * Setter for <code>public.email_activation_tokens_aud.user_id</code>.
     */
    public void setUserId(UUID value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.email_activation_tokens_aud.user_id</code>.
     */
    public UUID getUserId() {
        return (UUID) get(4);
    }

    /**
     * Setter for <code>public.email_activation_tokens_aud.token</code>.
     */
    public void setToken(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.email_activation_tokens_aud.token</code>.
     */
    public String getToken() {
        return (String) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<Integer, UUID> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Integer, Short, LocalDateTime, UUID, UUID, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Integer, Short, LocalDateTime, UUID, UUID, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return EmailActivationTokensAud.EMAIL_ACTIVATION_TOKENS_AUD.REV;
    }

    @Override
    public Field<Short> field2() {
        return EmailActivationTokensAud.EMAIL_ACTIVATION_TOKENS_AUD.REVTYPE;
    }

    @Override
    public Field<LocalDateTime> field3() {
        return EmailActivationTokensAud.EMAIL_ACTIVATION_TOKENS_AUD.EXPIRATION_DATE;
    }

    @Override
    public Field<UUID> field4() {
        return EmailActivationTokensAud.EMAIL_ACTIVATION_TOKENS_AUD.ID;
    }

    @Override
    public Field<UUID> field5() {
        return EmailActivationTokensAud.EMAIL_ACTIVATION_TOKENS_AUD.USER_ID;
    }

    @Override
    public Field<String> field6() {
        return EmailActivationTokensAud.EMAIL_ACTIVATION_TOKENS_AUD.TOKEN;
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
        return getExpirationDate();
    }

    @Override
    public UUID component4() {
        return getId();
    }

    @Override
    public UUID component5() {
        return getUserId();
    }

    @Override
    public String component6() {
        return getToken();
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
        return getExpirationDate();
    }

    @Override
    public UUID value4() {
        return getId();
    }

    @Override
    public UUID value5() {
        return getUserId();
    }

    @Override
    public String value6() {
        return getToken();
    }

    @Override
    public EmailActivationTokensAudRecord value1(Integer value) {
        setRev(value);
        return this;
    }

    @Override
    public EmailActivationTokensAudRecord value2(Short value) {
        setRevtype(value);
        return this;
    }

    @Override
    public EmailActivationTokensAudRecord value3(LocalDateTime value) {
        setExpirationDate(value);
        return this;
    }

    @Override
    public EmailActivationTokensAudRecord value4(UUID value) {
        setId(value);
        return this;
    }

    @Override
    public EmailActivationTokensAudRecord value5(UUID value) {
        setUserId(value);
        return this;
    }

    @Override
    public EmailActivationTokensAudRecord value6(String value) {
        setToken(value);
        return this;
    }

    @Override
    public EmailActivationTokensAudRecord values(Integer value1, Short value2, LocalDateTime value3, UUID value4, UUID value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached EmailActivationTokensAudRecord
     */
    public EmailActivationTokensAudRecord() {
        super(EmailActivationTokensAud.EMAIL_ACTIVATION_TOKENS_AUD);
    }

    /**
     * Create a detached, initialised EmailActivationTokensAudRecord
     */
    public EmailActivationTokensAudRecord(Integer rev, Short revtype, LocalDateTime expirationDate, UUID id, UUID userId, String token) {
        super(EmailActivationTokensAud.EMAIL_ACTIVATION_TOKENS_AUD);

        setRev(rev);
        setRevtype(revtype);
        setExpirationDate(expirationDate);
        setId(id);
        setUserId(userId);
        setToken(token);
        resetChangedOnNotNull();
    }
}