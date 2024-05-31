/*
 * This file is generated by jOOQ.
 */
package com.github.senocak.skj.jooq.tables.records;


import com.github.senocak.skj.jooq.tables.UserRoles;

import java.util.UUID;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserRolesRecord extends UpdatableRecordImpl<UserRolesRecord> implements Record2<UUID, UUID> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.user_roles.user_id</code>.
     */
    public void setUserId(UUID value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.user_roles.user_id</code>.
     */
    public UUID getUserId() {
        return (UUID) get(0);
    }

    /**
     * Setter for <code>public.user_roles.role_id</code>.
     */
    public void setRoleId(UUID value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.user_roles.role_id</code>.
     */
    public UUID getRoleId() {
        return (UUID) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<UUID, UUID> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<UUID, UUID> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<UUID, UUID> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<UUID> field1() {
        return UserRoles.USER_ROLES.USER_ID;
    }

    @Override
    public Field<UUID> field2() {
        return UserRoles.USER_ROLES.ROLE_ID;
    }

    @Override
    public UUID component1() {
        return getUserId();
    }

    @Override
    public UUID component2() {
        return getRoleId();
    }

    @Override
    public UUID value1() {
        return getUserId();
    }

    @Override
    public UUID value2() {
        return getRoleId();
    }

    @Override
    public UserRolesRecord value1(UUID value) {
        setUserId(value);
        return this;
    }

    @Override
    public UserRolesRecord value2(UUID value) {
        setRoleId(value);
        return this;
    }

    @Override
    public UserRolesRecord values(UUID value1, UUID value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserRolesRecord
     */
    public UserRolesRecord() {
        super(UserRoles.USER_ROLES);
    }

    /**
     * Create a detached, initialised UserRolesRecord
     */
    public UserRolesRecord(UUID userId, UUID roleId) {
        super(UserRoles.USER_ROLES);

        setUserId(userId);
        setRoleId(roleId);
        resetChangedOnNotNull();
    }
}