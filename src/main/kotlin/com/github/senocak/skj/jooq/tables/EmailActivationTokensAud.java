/*
 * This file is generated by jOOQ.
 */
package com.github.senocak.skj.jooq.tables;


import com.github.senocak.skj.jooq.Keys;
import com.github.senocak.skj.jooq.Public;
import com.github.senocak.skj.jooq.tables.records.EmailActivationTokensAudRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function6;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row6;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class EmailActivationTokensAud extends TableImpl<EmailActivationTokensAudRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.email_activation_tokens_aud</code>
     */
    public static final EmailActivationTokensAud EMAIL_ACTIVATION_TOKENS_AUD = new EmailActivationTokensAud();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<EmailActivationTokensAudRecord> getRecordType() {
        return EmailActivationTokensAudRecord.class;
    }

    /**
     * The column <code>public.email_activation_tokens_aud.rev</code>.
     */
    public final TableField<EmailActivationTokensAudRecord, Integer> REV = createField(DSL.name("rev"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.email_activation_tokens_aud.revtype</code>.
     */
    public final TableField<EmailActivationTokensAudRecord, Short> REVTYPE = createField(DSL.name("revtype"), SQLDataType.SMALLINT, this, "");

    /**
     * The column
     * <code>public.email_activation_tokens_aud.expiration_date</code>.
     */
    public final TableField<EmailActivationTokensAudRecord, LocalDateTime> EXPIRATION_DATE = createField(DSL.name("expiration_date"), SQLDataType.LOCALDATETIME(6), this, "");

    /**
     * The column <code>public.email_activation_tokens_aud.id</code>.
     */
    public final TableField<EmailActivationTokensAudRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.email_activation_tokens_aud.user_id</code>.
     */
    public final TableField<EmailActivationTokensAudRecord, UUID> USER_ID = createField(DSL.name("user_id"), SQLDataType.UUID, this, "");

    /**
     * The column <code>public.email_activation_tokens_aud.token</code>.
     */
    public final TableField<EmailActivationTokensAudRecord, String> TOKEN = createField(DSL.name("token"), SQLDataType.VARCHAR(64), this, "");

    private EmailActivationTokensAud(Name alias, Table<EmailActivationTokensAudRecord> aliased) {
        this(alias, aliased, null);
    }

    private EmailActivationTokensAud(Name alias, Table<EmailActivationTokensAudRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.email_activation_tokens_aud</code> table
     * reference
     */
    public EmailActivationTokensAud(String alias) {
        this(DSL.name(alias), EMAIL_ACTIVATION_TOKENS_AUD);
    }

    /**
     * Create an aliased <code>public.email_activation_tokens_aud</code> table
     * reference
     */
    public EmailActivationTokensAud(Name alias) {
        this(alias, EMAIL_ACTIVATION_TOKENS_AUD);
    }

    /**
     * Create a <code>public.email_activation_tokens_aud</code> table reference
     */
    public EmailActivationTokensAud() {
        this(DSL.name("email_activation_tokens_aud"), null);
    }

    public <O extends Record> EmailActivationTokensAud(Table<O> child, ForeignKey<O, EmailActivationTokensAudRecord> key) {
        super(child, key, EMAIL_ACTIVATION_TOKENS_AUD);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<EmailActivationTokensAudRecord> getPrimaryKey() {
        return Keys.EMAIL_ACTIVATION_TOKENS_AUD_PKEY;
    }

    @Override
    public List<ForeignKey<EmailActivationTokensAudRecord, ?>> getReferences() {
        return Arrays.asList(Keys.EMAIL_ACTIVATION_TOKENS_AUD__FK_EMAIL_ACTIVATION_TOKENS_AUDIT_REV);
    }

    private transient AuditRevisionEntity _auditRevisionEntity;

    /**
     * Get the implicit join path to the
     * <code>public.audit_revision_entity</code> table.
     */
    public AuditRevisionEntity auditRevisionEntity() {
        if (_auditRevisionEntity == null)
            _auditRevisionEntity = new AuditRevisionEntity(this, Keys.EMAIL_ACTIVATION_TOKENS_AUD__FK_EMAIL_ACTIVATION_TOKENS_AUDIT_REV);

        return _auditRevisionEntity;
    }

    @Override
    public EmailActivationTokensAud as(String alias) {
        return new EmailActivationTokensAud(DSL.name(alias), this);
    }

    @Override
    public EmailActivationTokensAud as(Name alias) {
        return new EmailActivationTokensAud(alias, this);
    }

    @Override
    public EmailActivationTokensAud as(Table<?> alias) {
        return new EmailActivationTokensAud(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public EmailActivationTokensAud rename(String name) {
        return new EmailActivationTokensAud(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public EmailActivationTokensAud rename(Name name) {
        return new EmailActivationTokensAud(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public EmailActivationTokensAud rename(Table<?> name) {
        return new EmailActivationTokensAud(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row6<Integer, Short, LocalDateTime, UUID, UUID, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function6<? super Integer, ? super Short, ? super LocalDateTime, ? super UUID, ? super UUID, ? super String, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function6<? super Integer, ? super Short, ? super LocalDateTime, ? super UUID, ? super UUID, ? super String, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
