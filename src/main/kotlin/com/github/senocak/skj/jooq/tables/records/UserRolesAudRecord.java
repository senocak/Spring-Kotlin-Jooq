/*
 * This file is generated by jOOQ.
 */
package com.github.senocak.skj.jooq.tables.records;


import com.github.senocak.skj.jooq.tables.UserRolesAud;

import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserRolesAudRecord extends UpdatableRecordImpl<UserRolesAudRecord> implements Record4<Integer, Short, UUID, UUID> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.user_roles_aud.rev</code>.
     */
    public void setRev(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.user_roles_aud.rev</code>.
     */
    public Integer getRev() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.user_roles_aud.revtype</code>.
     */
    public void setRevtype(Short value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.user_roles_aud.revtype</code>.
     */
    public Short getRevtype() {
        return (Short) get(1);
    }

    /**
     * Setter for <code>public.user_roles_aud.role_id</code>.
     */
    public void setRoleId(UUID value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.user_roles_aud.role_id</code>.
     */
    public UUID getRoleId() {
        return (UUID) get(2);
    }

    /**
     * Setter for <code>public.user_roles_aud.user_id</code>.
     */
    public void setUserId(UUID value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.user_roles_aud.user_id</code>.
     */
    public UUID getUserId() {
        return (UUID) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record3<Integer, UUID, UUID> key() {
        return (Record3) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, Short, UUID, UUID> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Integer, Short, UUID, UUID> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return UserRolesAud.USER_ROLES_AUD.REV;
    }

    @Override
    public Field<Short> field2() {
        return UserRolesAud.USER_ROLES_AUD.REVTYPE;
    }

    @Override
    public Field<UUID> field3() {
        return UserRolesAud.USER_ROLES_AUD.ROLE_ID;
    }

    @Override
    public Field<UUID> field4() {
        return UserRolesAud.USER_ROLES_AUD.USER_ID;
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
    public UUID component3() {
        return getRoleId();
    }

    @Override
    public UUID component4() {
        return getUserId();
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
    public UUID value3() {
        return getRoleId();
    }

    @Override
    public UUID value4() {
        return getUserId();
    }

    @Override
    public UserRolesAudRecord value1(Integer value) {
        setRev(value);
        return this;
    }

    @Override
    public UserRolesAudRecord value2(Short value) {
        setRevtype(value);
        return this;
    }

    @Override
    public UserRolesAudRecord value3(UUID value) {
        setRoleId(value);
        return this;
    }

    @Override
    public UserRolesAudRecord value4(UUID value) {
        setUserId(value);
        return this;
    }

    @Override
    public UserRolesAudRecord values(Integer value1, Short value2, UUID value3, UUID value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserRolesAudRecord
     */
    public UserRolesAudRecord() {
        super(UserRolesAud.USER_ROLES_AUD);
    }

    /**
     * Create a detached, initialised UserRolesAudRecord
     */
    public UserRolesAudRecord(Integer rev, Short revtype, UUID roleId, UUID userId) {
        super(UserRolesAud.USER_ROLES_AUD);

        setRev(rev);
        setRevtype(revtype);
        setRoleId(roleId);
        setUserId(userId);
        resetChangedOnNotNull();
    }
}
