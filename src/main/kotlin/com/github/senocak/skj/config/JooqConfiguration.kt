package com.github.senocak.skj.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.stereotype.Component
import javax.sql.DataSource
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration

@Component
@ConfigurationProperties(prefix = "spring.datasource")
class JooqConfiguration {
    var url: String? = null
    var username: String? = null
    var password: String? = null
    var driverClassName: String? = null

    @Bean
    @Primary
    fun dataSource(): DataSource =
        when {
            url!!.contains(other = "jdbc:postgresql") -> DriverManagerDataSource()
                .also { db: DriverManagerDataSource ->
                    db.setDriverClassName("org.postgresql.Driver")
                    db.url = url
                    db.username = username
                    db.password = password
                }
            else -> throw RuntimeException("Not configured")
        }

    @Bean
    fun dsl(dataSource: DataSource): DSLContext = run {
        val configuration = DefaultConfiguration()
            .set(dataSource)
            .set(SQLDialect.POSTGRES)
        DSL.using(configuration)
    }

    override fun toString(): String =
        "DataSourceConfig(url=$url, username=$username, password=$password, driverClassName=$driverClassName)"
}
