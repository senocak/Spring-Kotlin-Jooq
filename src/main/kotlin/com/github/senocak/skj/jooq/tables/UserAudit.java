/*
 * This file is generated by jOOQ.
 */
package com.github.senocak.skj.jooq.tables;


import com.github.senocak.skj.jooq.Keys;
import com.github.senocak.skj.jooq.Public;
import com.github.senocak.skj.jooq.tables.records.UserAuditRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function7;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row7;
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
public class UserAudit extends TableImpl<UserAuditRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.user_audit</code>
     */
    public static final UserAudit USER_AUDIT = new UserAudit();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserAuditRecord> getRecordType() {
        return UserAuditRecord.class;
    }

    /**
     * The column <code>public.user_audit.rev</code>.
     */
    public final TableField<UserAuditRecord, Integer> REV = createField(DSL.name("rev"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>public.user_audit.revtype</code>.
     */
    public final TableField<UserAuditRecord, Short> REVTYPE = createField(DSL.name("revtype"), SQLDataType.SMALLINT, this, "");

    /**
     * The column <code>public.user_audit.email_activated_at</code>.
     */
    public final TableField<UserAuditRecord, LocalDateTime> EMAIL_ACTIVATED_AT = createField(DSL.name("email_activated_at"), SQLDataType.LOCALDATETIME(6), this, "");

    /**
     * The column <code>public.user_audit.id</code>.
     */
    public final TableField<UserAuditRecord, UUID> ID = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>public.user_audit.email</code>.
     */
    public final TableField<UserAuditRecord, String> EMAIL = createField(DSL.name("email"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.user_audit.name</code>.
     */
    public final TableField<UserAuditRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>public.user_audit.password</code>.
     */
    public final TableField<UserAuditRecord, String> PASSWORD = createField(DSL.name("password"), SQLDataType.VARCHAR(255), this, "");

    private UserAudit(Name alias, Table<UserAuditRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserAudit(Name alias, Table<UserAuditRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.user_audit</code> table reference
     */
    public UserAudit(String alias) {
        this(DSL.name(alias), USER_AUDIT);
    }

    /**
     * Create an aliased <code>public.user_audit</code> table reference
     */
    public UserAudit(Name alias) {
        this(alias, USER_AUDIT);
    }

    /**
     * Create a <code>public.user_audit</code> table reference
     */
    public UserAudit() {
        this(DSL.name("user_audit"), null);
    }

    public <O extends Record> UserAudit(Table<O> child, ForeignKey<O, UserAuditRecord> key) {
        super(child, key, USER_AUDIT);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<UserAuditRecord> getPrimaryKey() {
        return Keys.USER_AUDIT_PKEY;
    }

    @Override
    public List<ForeignKey<UserAuditRecord, ?>> getReferences() {
        return Arrays.asList(Keys.USER_AUDIT__FK_USER_AUDIT_REV);
    }

    private transient AuditRevisionEntity _auditRevisionEntity;

    /**
     * Get the implicit join path to the
     * <code>public.audit_revision_entity</code> table.
     */
    public AuditRevisionEntity auditRevisionEntity() {
        if (_auditRevisionEntity == null)
            _auditRevisionEntity = new AuditRevisionEntity(this, Keys.USER_AUDIT__FK_USER_AUDIT_REV);

        return _auditRevisionEntity;
    }

    @Override
    public UserAudit as(String alias) {
        return new UserAudit(DSL.name(alias), this);
    }

    @Override
    public UserAudit as(Name alias) {
        return new UserAudit(alias, this);
    }

    @Override
    public UserAudit as(Table<?> alias) {
        return new UserAudit(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserAudit rename(String name) {
        return new UserAudit(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserAudit rename(Name name) {
        return new UserAudit(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public UserAudit rename(Table<?> name) {
        return new UserAudit(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<Integer, Short, LocalDateTime, UUID, String, String, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function7<? super Integer, ? super Short, ? super LocalDateTime, ? super UUID, ? super String, ? super String, ? super String, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function7<? super Integer, ? super Short, ? super LocalDateTime, ? super UUID, ? super String, ? super String, ? super String, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}