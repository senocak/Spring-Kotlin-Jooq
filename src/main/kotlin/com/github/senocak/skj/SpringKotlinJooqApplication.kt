package com.github.senocak.skj

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

fun main(args: Array<String>) {
    runApplication<SpringKotlinJooqApplication>(*args)
}

@SpringBootApplication
class SpringKotlinJooqApplication