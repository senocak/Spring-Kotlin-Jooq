package com.github.senocak.skj.security

import com.github.senocak.skj.util.logger
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class JwtTokenProvider {
    private val log: Logger by logger()

    @Value("\${app.jwtSecret}")
    private lateinit var jwtSecret: String

    @Value("\${app.jwtExpirationInMs}")
    private lateinit var jwtExpirationInMs: String

    /**
     * Generating the jwt token
     * @param email -- email
     */
    fun generateJwtToken(email: String, roles: List<String?>): String =
        generateToken(subject = email, roles = roles, expirationInMs = jwtExpirationInMs.toLong())

    /**
     * Generating the token
     * @param subject -- userId
     */
    private fun generateToken(subject: String, roles: List<String?>, expirationInMs: Long): String =
        HashMap<String, Any>()
            .also { it["roles"] = roles }
            .run {
                val now = Date()
                Jwts.builder()
                    .setClaims(this)
                    .setSubject(subject)
                    .setIssuedAt(now)
                    .setExpiration(Date(now.time + expirationInMs))
                    .signWith(signKey, SignatureAlgorithm.HS256)
                    .compact()
            }

    private val signKey: Key
        get() = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))

    /**
     * Get the jws claims
     * @param token -- jwt token
     * @return -- expiration date
     */
    @Throws(
        ExpiredJwtException::class,
        UnsupportedJwtException::class,
        MalformedJwtException::class,
        SignatureException::class,
        IllegalArgumentException::class
    )
    private fun getJwsClaims(token: String): Jws<Claims?> = Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token)

    /**
     * @param token -- jwt token
     * @return -- userName from jwt
     */
    fun getUserEmailFromJWT(token: String): String = getJwsClaims(token).body!!.subject.run { this }

    /**
     * @param token -- jwt token
     */
    fun validateToken(token: String): String {
        try {
            return getUserEmailFromJWT(token = token)
        } catch (ex: SignatureException) {
            "Invalid JWT signature"
                .apply { log.error(this) }
                .run { throw AccessDeniedException(this) }
        } catch (ex: MalformedJwtException) {
            "Invalid JWT token"
                .apply { log.error(this) }
                .run { throw AccessDeniedException(this) }
        } catch (ex: ExpiredJwtException) {
            "Expired JWT token"
                .apply { log.error(this) }
                .run { throw AccessDeniedException(this) }
        } catch (ex: UnsupportedJwtException) {
            "Unsupported JWT token"
                .apply { log.error(this) }
                .run { throw AccessDeniedException(this) }
        } catch (ex: IllegalArgumentException) {
            "JWT claims string is empty."
                .apply { log.error(this) }
                .run { throw AccessDeniedException(this) }
        } catch (ex: AccessDeniedException) {
            ex.message
                .apply { log.error(this) }
                .run { throw AccessDeniedException(this) }
        } catch (ex: Exception) {
            "Undefined exception occurred: ${ex.message}"
                .apply { log.error(this) }
                .run { throw AccessDeniedException(this) }
        }
    }
}
