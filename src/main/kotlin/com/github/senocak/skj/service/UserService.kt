package com.github.senocak.skj.service

import com.github.senocak.skj.domain.Role
import com.github.senocak.skj.domain.User
import com.github.senocak.skj.exception.ServerException
import com.github.senocak.skj.jooq.Tables.ROLES
import com.github.senocak.skj.jooq.Tables.USERS
import com.github.senocak.skj.jooq.Tables.USER_ROLES
import com.github.senocak.skj.jooq.tables.records.UsersRecord
import com.github.senocak.skj.util.RoleName
import com.github.senocak.skj.util.logger
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import org.slf4j.Logger
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.Result
import org.jooq.impl.DSL.count
import org.jooq.impl.DSL.multiset
import org.jooq.impl.DSL.noCondition
import org.jooq.impl.DSL.select

@Service
class UserService(
    private val passwordEncoder: PasswordEncoder,
    private var dsl: DSLContext
) : UserDetailsService {
    private val log: Logger by logger()

    fun findAllUsers(email: String? = null, name: String? = null, offset: Int = 0, limit: Int = 100): List<User> = run {
        val specifications = arrayListOf<Condition>()
        if (email != null)
            specifications.add(element = USERS.EMAIL.contains(email))
        if (name != null)
            specifications.add(element = USERS.NAME.contains(name))
        var condition: Condition = noCondition()
        specifications.forEach { spec -> condition = condition.and(spec) }
        val posts2 = dsl
            .select(
                USERS.ID.`as`("id"),
                USERS.NAME,
                USERS.EMAIL,
                USERS.PASSWORD,
                USERS.EMAIL_ACTIVATED_AT,
                multiset(
                    select(ROLES.ID, ROLES.NAME)
                        .from(USER_ROLES)
                        .join(ROLES).on(ROLES.ID.eq(USER_ROLES.ROLE_ID))
                        .where(USER_ROLES.USER_ID.eq(USERS.ID))
                ).`as`("roles")
            )
            .from(USERS)
            .where(condition)
            .orderBy(USERS.ID.asc()) // Add ordering to ensure consistent pagination
            .offset(offset)
            .limit(limit)
            .fetch()
            // .fetchInto(User::class.java) implicity casting to dto
        val usersWithRoles: MutableList<User> = posts2.map { record ->
            val userId = record[USERS.ID]
            val userName = record[USERS.NAME]
            val userEmail = record[USERS.EMAIL]
            val userPassword = record[USERS.PASSWORD]
            val email_activated_at = record[USERS.EMAIL_ACTIVATED_AT]
            val rolesResult = record.get("roles", Result::class.java)
            val roles = rolesResult?.map { roleRecord ->
                Role(name = RoleName.fromString(r = roleRecord.get(ROLES.NAME))).also { it.id = roleRecord.get(ROLES.ID) }
            } ?: emptyList()
            User(name = userName, email = userEmail, password = userPassword)
                .also {
                    it.id = UUID.fromString(userId.toString())
                    it.roles = roles
                    it.emailActivatedAt = Date.from(email_activated_at.atZone(ZoneId.systemDefault()).toInstant())
                }
        }
        usersWithRoles
    }

    fun findById(id: UUID): User =
        dsl.selectFrom(USERS)
            .where(USERS.ID.eq(id))
            .fetchOneInto(User::class.java)  // Fetch the result into a User POJO
            ?: throw UsernameNotFoundException("user_not_found")

    fun findByEmail(email: String): User {
        val record = dsl.select(
            USERS.ID.`as`("id"),
            USERS.NAME,
            USERS.EMAIL,
            USERS.PASSWORD,
            USERS.EMAIL_ACTIVATED_AT,
            multiset(
                select(ROLES.ID, ROLES.NAME)
                    .from(USER_ROLES)
                    .join(ROLES).on(USER_ROLES.ROLE_ID.eq(ROLES.ID))
                    .where(USER_ROLES.USER_ID.eq(USERS.ID))
            ).`as`("roles")
        )
            .from(USERS)
            .where(USERS.EMAIL.eq(email))
            .fetchOne()  // Fetch a single record
            ?: throw UsernameNotFoundException("user_not_found")

        return record.let {
            val userId = it.getValue(USERS.ID)
            val userName = it.getValue(USERS.NAME)
            val userEmail = it.getValue(USERS.EMAIL)
            val userPassword = it.getValue(USERS.PASSWORD)
            val rolesResult = it.get("roles", Result::class.java)
            // Extracting the list of roles from the multiset field
            val roles = rolesResult?.map { roleRecord ->
                Role(name = RoleName.fromString(r = roleRecord.get(ROLES.NAME))).also { it.id = roleRecord.get(ROLES.ID) }
            } ?: emptyList()
            val email_activated_at = it.getValue(USERS.EMAIL_ACTIVATED_AT)
            User(name = userName, email = userEmail, password = userPassword)
                .also { u ->
                    u.id = UUID.fromString(userId.toString())
                    u.roles = roles
                    u.emailActivatedAt = Date.from(email_activated_at.atZone(ZoneId.systemDefault()).toInstant())
                }
        }
    }

    fun existsByEmail(email: String): Boolean {
        val count: Int = dsl.select(count())
            .from(USERS)
            .where(USERS.EMAIL.eq(email))
            .fetchOne(0, Int::class.java)  // Fetch the count as an integer
            ?: 0
        return count > 0
    }

    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): org.springframework.security.core.userdetails.User {
        val user: User = findByEmail(email = email)
        val authorities: List<GrantedAuthority> = user.roles.stream()
            .map { r: Role -> SimpleGrantedAuthority(RoleName.fromString(r = r.name.toString())!!.name) }
            .toList()
        return org.springframework.security.core.userdetails.User(user.email, user.password, authorities)
    }

    @Throws(ServerException::class)
    fun loggedInUser(): User =
        (SecurityContextHolder.getContext().authentication.principal as org.springframework.security.core.userdetails.User).username
            .run { findByEmail(email = this) }

    fun insert(user: User) =
        dsl.insertInto(USERS)
            .columns(USERS.NAME, USERS.EMAIL, USERS.PASSWORD)
            .values(user.name, user.email, passwordEncoder.encode(user.password))
            .returningResult(USERS.ID)
            .fetchOneInto(Long::class.java)

    fun save(user: User): User = run {
        val newRecord: UsersRecord = dsl.newRecord(USERS)
        newRecord.name = user.name
        newRecord.email = user.email
        newRecord.password = passwordEncoder.encode(user.password)
        newRecord.emailActivatedAt = LocalDateTime.now()
        // set role
        newRecord.store()
        user
    }

    fun delete(id: UUID) = dsl.deleteFrom(USERS).where(USERS.ID.eq(id)).execute()

    fun updateUserById(userId: UUID, name: String?, email: String?, password: String?) =
        dsl
            .update(USERS)
            .set(USERS.NAME, name)
            .set(USERS.EMAIL, email)
            .set(USERS.PASSWORD, password)
            .where(USERS.ID.eq(userId))
            .execute()
}
