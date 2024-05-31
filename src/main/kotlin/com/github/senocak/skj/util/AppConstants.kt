package com.github.senocak.skj.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object AppConstants {
    const val DEFAULT_PAGE_NUMBER = "0"
    const val DEFAULT_PAGE_SIZE = "10"
    const val TOKEN_HEADER_NAME = "Authorization"
    const val TOKEN_PREFIX = "Bearer "
    const val ADMIN = "ADMIN"
    const val USER = "USER"
    const val SECURITY_SCHEME_NAME = "bearerAuth"
}

fun <R : Any> R.logger(): Lazy<Logger> = lazy {
    LoggerFactory.getLogger((if (javaClass.kotlin.isCompanion) javaClass.enclosingClass else javaClass).name)
}