package com.github.senocak.skj.config

import com.github.senocak.skj.security.JwtAuthenticationEntryPoint
import com.github.senocak.skj.security.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val unauthorizedHandler: JwtAuthenticationEntryPoint,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

    /**
     * Override this method to configure the HttpSecurity.
     * @param http -- It allows configuring web based security for specific http requests
     * @throws Exception -- throws Exception
     */
    @Throws(Exception::class)
    @Bean
    fun securityFilterChainDSL(http: HttpSecurity): SecurityFilterChain =
        http {
            cors {
               configurationSource = UrlBasedCorsConfigurationSource()
                   .also { ubccs: UrlBasedCorsConfigurationSource ->
                       ubccs.registerCorsConfiguration("/**", CorsConfiguration()
                           .also {cc: CorsConfiguration ->
                               cc.allowCredentials = true
                               cc.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
                               cc.allowedOrigins = listOf("https://example.com")
                               cc.allowedHeaders = listOf("Authorization", "Origin", "Content-Type", "Accept")
                               cc.exposedHeaders = listOf("Content-Type", "X-Rate-Limit-Retry-After-Seconds", "X-Rate-Limit-Remaining")
                           })
                   }
            }
            csrf { disable() }
            exceptionHandling { authenticationEntryPoint = unauthorizedHandler }
            authorizeRequests {
                authorize(pattern = "/api/v1/auth/**", access = permitAll)
                authorize(pattern = "/api/v1/swagger/**", access = permitAll)
                authorize(pattern = "/swagger**/**", access = permitAll)
                authorize(pattern = "/ws/**", access = permitAll)
                authorize(matches = anyRequest, access = authenticated)
            }
            sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }
            headers { frameOptions { disable() } }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(filter = jwtAuthenticationFilter)
        }
            .run { http.build() }
}
