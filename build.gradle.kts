import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.Configuration
import org.jooq.meta.jaxb.Database
import org.jooq.meta.jaxb.Generator
import org.jooq.meta.jaxb.Jdbc
import org.jooq.meta.jaxb.Target
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.MountableFile

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.postgresql:postgresql:42.5.0")
        classpath("org.testcontainers:postgresql:1.17.6")
        classpath("org.jooq:jooq-codegen:3.18.7")
    }
}

plugins {
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
}

group = "com.github.senocak"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

sourceSets {
    main {
        java {
            srcDirs("src/main/kotlin")
        }
    }
}
val jjwt = "0.11.5"
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.data:spring-data-commons")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
    implementation("io.jsonwebtoken:jjwt-api:$jjwt")
    implementation("io.jsonwebtoken:jjwt-impl:$jjwt")
    implementation("io.jsonwebtoken:jjwt-jackson:$jjwt")
    implementation("org.passay:passay:1.6.2")

    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<KotlinCompile> {
    dependsOn("jooqCodegenExistDb")
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}
tasks.withType<Test> {
    useJUnitPlatform()
}
tasks.register("jooqCodegenExistDb") {
    doLast {
        val configuration = Configuration()
            .withJdbc(
                Jdbc()
                    .withDriver("org.postgresql.Driver")
                    .withUrl("jdbc:postgresql://localhost:5432/boilerplate?currentSchema=public")
                    .withUser("postgres")
                    .withPassword("senocak")
            )
            .withGenerator(
                Generator()
                    .withDatabase(
                        Database()
                            .withName("org.jooq.meta.postgres.PostgresDatabase")
                            .withInputSchema("public")
                            .withIncludes(".*")  // include all tables
                            .withExcludes("")    // exclude nothing
                            .withForcedTypes()
                    )
                    .withTarget(
                        Target()
                            .withPackageName("com.github.senocak.skj.jooq")
                            .withDirectory(layout.projectDirectory.dir("src/main/kotlin").asFile.path)
                    )
            )
        GenerationTool.generate(configuration)
    }
}
tasks.register("jooqCodegen") {
    doLast {
        val schemaPath = layout.projectDirectory.file("src/main/resources/schema.sql").asFile.path
        val containerInstance = PostgreSQLContainer<Nothing>("postgres:14").apply {
            withCopyFileToContainer(MountableFile.forHostPath(schemaPath), "/docker-entrypoint-initdb.d/init.sql")
            start()
        }
        try {
            val configuration = Configuration()
                .withJdbc(
                    Jdbc()
                        .withDriver("org.postgresql.Driver")
                        .withUrl(containerInstance.jdbcUrl)
                        .withUser(containerInstance.username)
                        .withPassword(containerInstance.password)
                )
                .withGenerator(
                    Generator()
                        .withDatabase(
                            Database()
                                .withName("org.jooq.meta.postgres.PostgresDatabase")
                                .withInputSchema("public")
                                .withIncludes(".*")  // include all tables
                                .withExcludes("")    // exclude nothing
                        )
                        .withTarget(
                            Target()
                                .withPackageName("org.jooq.generated")
                                .withDirectory(layout.projectDirectory.dir("src/main/kotlin").asFile.path)
                        )
                )
            GenerationTool.generate(configuration)
        } finally {
            containerInstance.stop()
        }
    }
}