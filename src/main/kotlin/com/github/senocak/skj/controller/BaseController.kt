package com.github.senocak.skj.controller

import com.github.senocak.skj.exception.ServerException
import com.github.senocak.skj.util.OmaErrorMessageType
import org.springframework.http.HttpHeaders
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.CrossOrigin

@CrossOrigin(origins = ["*"], maxAge = 3600)
abstract class BaseController {
    fun validate(resultOfValidation: BindingResult) {
        if (resultOfValidation.hasErrors()) {
            arrayListOf<String>()
                .apply {
                    resultOfValidation.fieldErrors.forEach {
                            fieldError: FieldError? ->
                        this.add(element = "${fieldError?.field}: ${fieldError?.defaultMessage}")
                    }
                }
                .apply {
                    resultOfValidation.globalErrors.forEach { err: ObjectError? ->
                        this.add(element = "${err?.defaultMessage}")
                    }
                }
                .run {
                    throw ServerException(omaErrorMessageType = OmaErrorMessageType.JSON_SCHEMA_VALIDATOR,
                        variables = this.toTypedArray())
                }
        }
    }

    /**
     * Creates an HTTP header containing a user ID.
     * @param userId The user ID to include in the header.
     * @return The HttpHeaders object containing the user ID header.
     */
    protected fun userIdHeader(userId: String): HttpHeaders =
        HttpHeaders()
            .apply { this.add("userId", userId) }

    companion object {
        private const val API = "/api"
        const val V1 = "$API/v1"
    }
}
