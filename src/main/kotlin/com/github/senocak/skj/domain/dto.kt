package com.github.senocak.skj.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName
import com.github.senocak.skj.util.RoleName
import com.github.senocak.skj.util.validation.PasswordMatches
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.util.Date
import java.util.UUID

open class BaseDomain(
    var id: UUID? = null,
    createdAt: Date = Date(),
    updatedAt: Date = Date()
)

@JsonIgnoreProperties(ignoreUnknown = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
abstract class BaseDto

@JsonPropertyOrder("id", "name", "username", "email", "roles", "emailActivatedAt")
data class User(
    @JsonProperty("name")
    @Schema(example = "Lorem Ipsum", description = "Name of the user", required = true, name = "name", type = "String")
    var name: String? = null,

    @Schema(example = "lorem@ipsum.com", description = "Email of the user", required = true, name = "email", type = "String")
    var email: String? = null,

    @Schema(example = "***", description = "Password of the user", required = false, name = "password", type = "String")
    var password: String? = null
) : BaseDomain() {
    @ArraySchema(schema = Schema(example = "ROLE_USER", description = "Roles of the user", required = true, name = "roles"))
    var roles: List<Role> = arrayListOf()
    var emailActivatedAt: Date? = null
}

@JsonPropertyOrder("user", "token")
class UserWrapperResponse(
    @Schema(required = true)
    var user: User,

    @Schema(example = "eyJraWQiOiJ...", description = "Jwt Token", required = false, name = "token", type = "String")
    var token: String,
) : BaseDto()

data class Role(
    @Schema(example = "ROLE_USER", description = "Name of the role", required = true, name = "name")
    var name: RoleName? = null
) : BaseDomain()

@PasswordMatches
data class UpdateUserDto(
    @Schema(example = "Anil", description = "Name", required = true, name = "name", type = "String")
    @field:Size(min = 4, max = 40)
    var name: String? = null,

    @Schema(example = "Anil123", description = "Password", required = true, name = "password", type = "String")
    var password: String? = null,

    @JsonProperty("password_confirmation")
    @Schema(example = "Anil123", description = "Password confirmation", required = true, name = "password", type = "String")
    var passwordConfirmation: String? = null
) : BaseDto()

@JsonPropertyOrder("offset", "limit", "total", "items")
data class PaginationResponse<P>(
    @Schema(example = "1", description = "Current page", required = true, name = "page", type = "String")
    val offset: Int,

    @Schema(example = "3", description = "Total pages", required = true, name = "pages", type = "String")
    val limit: Int,

    @Schema(example = "10", description = "Total elements", required = true, name = "total", type = "String")
    val total: Int,

    @ArraySchema(schema = Schema(description = "items", required = true, type = "ListDto"))
    val items: List<P>,
) : BaseDto()

data class LoginRequest(
    @JsonProperty("email")
    @Schema(example = "asenocak", description = "Email of the user", required = true, name = "email", type = "String")
    @field:NotBlank(message = "{not_blank}")
    @field:Size(min = 3, max = 50, message = "{min_max_length}")
    var email: String,

    @Schema(description = "Password of the user", name = "password", type = "String", example = "password", required = true)
    @field:NotBlank(message = "{not_blank}")
    @field:Size(min = 6, max = 20, message = "{min_max_length}")
    var password: String
) : BaseDto()

@JsonPropertyOrder("statusCode", "error", "variables")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName("exception")
class ExceptionDto : BaseDto() {
    var statusCode = 200
    var error: OmaErrorMessageTypeDto? = null
    var variables: Array<String?> = arrayOf(String())

    @JsonPropertyOrder("id", "text")
    class OmaErrorMessageTypeDto(val id: String? = null, val text: String? = null)
}
