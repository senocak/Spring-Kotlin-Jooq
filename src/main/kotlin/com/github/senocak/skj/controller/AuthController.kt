package com.github.senocak.skj.controller

import com.github.senocak.skj.domain.ExceptionDto
import com.github.senocak.skj.domain.LoginRequest
import com.github.senocak.skj.domain.PaginationResponse
import com.github.senocak.skj.domain.Role
import com.github.senocak.skj.domain.UpdateUserDto
import com.github.senocak.skj.domain.User
import com.github.senocak.skj.domain.UserWrapperResponse
import com.github.senocak.skj.exception.ServerException
import com.github.senocak.skj.security.Authorize
import com.github.senocak.skj.security.JwtTokenProvider
import com.github.senocak.skj.service.UserService
import com.github.senocak.skj.util.AppConstants.ADMIN
import com.github.senocak.skj.util.AppConstants.DEFAULT_PAGE_NUMBER
import com.github.senocak.skj.util.AppConstants.DEFAULT_PAGE_SIZE
import com.github.senocak.skj.util.AppConstants.SECURITY_SCHEME_NAME
import com.github.senocak.skj.util.AppConstants.USER
import com.github.senocak.skj.util.OmaErrorMessageType
import com.github.senocak.skj.util.RoleName
import com.github.senocak.skj.util.logger
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping(BaseController.V1)
@Tag(name = "Authentication", description = "AUTH API")
class AuthController(
    private val userService: UserService,
    private val tokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    @Value("\${app.jwtExpirationInMs}") private val jwtExpirationInMs: Long
) : BaseController() {
    private val log: Logger by logger()

    @PostMapping("/auth/login")
    @Operation(summary = "Login Endpoint", tags = ["Authentication"])
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "successful operation",
                content = arrayOf(Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = UserWrapperResponse::class)))
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad credentials",
                content = arrayOf(Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = ExceptionDto::class)))
            ),
            ApiResponse(
                responseCode = "500",
                description = "internal server error occurred",
                content = arrayOf(Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = ExceptionDto::class)))
            )
        ]
    )
    @Throws(ServerException::class)
    fun login(
        @Parameter(description = "Request body to login", required = true) @Validated @RequestBody loginRequest: LoginRequest,
        resultOfValidation: BindingResult
    ): ResponseEntity<UserWrapperResponse> =
        validate(resultOfValidation = resultOfValidation)
            .run { authenticationManager.authenticate(UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)) }
            .run { userService.findByEmail(email = loginRequest.email) }
            .apply {
                if (this.emailActivatedAt == null) {
                    "email_not_activated"
                        .also { msg: String ->
                            log.error(msg)
                            throw ServerException(omaErrorMessageType = OmaErrorMessageType.UNAUTHORIZED,
                                statusCode = HttpStatus.UNAUTHORIZED, variables = arrayOf(msg))
                        }
                }
            }
            .run {
                val generateUserWrapperResponse = generateUserWrapperResponse(user = this)
                val httpHeaders = userIdHeader(userId = "${this.id}")
                    .apply { this.add("jwtExpiresIn", "$jwtExpirationInMs") }
                ResponseEntity.ok().headers(httpHeaders).body(generateUserWrapperResponse)
            }

    @Throws(ServerException::class)
    @Operation(
        summary = "Get me",
        tags = ["User"],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "successful operation",
                content = arrayOf(Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = UserWrapperResponse::class)))
            ),
            ApiResponse(
                responseCode = "500",
                description = "internal server error occurred",
                content = arrayOf(Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = ExceptionDto::class)))
            )
        ],
        security = [SecurityRequirement(name = SECURITY_SCHEME_NAME, scopes = [ADMIN, USER])]
    )
    @Authorize(roles = [ADMIN, USER])
    @GetMapping("/user/me")
    fun me(): User = userService.loggedInUser()

    @PatchMapping("/user/me")
    @Operation(
        summary = "Update user by username",
        tags = ["User"],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "successful operation",
                content = arrayOf(Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = HashMap::class)))
            ),
            ApiResponse(
                responseCode = "500",
                description = "internal server error occurred",
                content = arrayOf(Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = ExceptionDto::class)))
            )
        ],
        security = [SecurityRequirement(name = SECURITY_SCHEME_NAME, scopes = [ADMIN, USER])]
    )
    @Authorize(roles = [ADMIN, USER])
    @Throws(ServerException::class)
    fun patchMe(
        request: HttpServletRequest,
        @Parameter(description = "Request body to update", required = true) @Validated @RequestBody userDto: UpdateUserDto,
        resultOfValidation: BindingResult
    ): User = run{
        validate(resultOfValidation = resultOfValidation)
        val user: User = userService.loggedInUser()
        val name: String? = userDto.name
        if (!name.isNullOrEmpty()) {
            user.name = name
        }
        val password: String? = userDto.password
        val passwordConfirmation: String? = userDto.passwordConfirmation
        if (!password.isNullOrEmpty()) {
            if (passwordConfirmation.isNullOrEmpty()) {
                "password_confirmation_not_provided"
                    .apply { log.error(this) }
                    .run {
                        throw ServerException(omaErrorMessageType = OmaErrorMessageType.BASIC_INVALID_INPUT,
                            variables = arrayOf(this), statusCode = HttpStatus.BAD_REQUEST)
                    }
            }
            if (passwordConfirmation != password) {
                "password_and_confirmation_not_matched"
                    .apply { log.error(this) }
                    .run {
                        throw ServerException(omaErrorMessageType = OmaErrorMessageType.BASIC_INVALID_INPUT,
                            variables = arrayOf(this), statusCode = HttpStatus.BAD_REQUEST)
                    }
            }
            user.password = passwordEncoder.encode(password)
        }
        userService.updateUserById(userId = user.id!!, name = user.name, email = user.email, password = user.password)
        user
    }

    @Throws(ServerException::class)
    @Operation(
        summary = "All Users",
        tags = ["User"],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = MediaType.APPLICATION_JSON_VALUE,
                content = arrayOf(Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = PaginationResponse::class)))
            ),
            ApiResponse(
                responseCode = "500",
                description = "internal server error occurred",
                content = arrayOf(Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = Schema(implementation = ExceptionDto::class)))
            )
        ],
        security = [SecurityRequirement(name = SECURITY_SCHEME_NAME, scopes = [ADMIN])]
    )
    @Authorize(roles = [ADMIN])
    @GetMapping("/user")
    fun allUsers(
        @Parameter(name = "page", description = "Page number", example = DEFAULT_PAGE_NUMBER)
        @RequestParam(defaultValue = "0", required = false)
        offset: Int,
        @Parameter(name = "size", description = "Page size", example = DEFAULT_PAGE_SIZE)
        @RequestParam(defaultValue = "\${spring.data.web.pageable.default-page-size:10}", required = false)
        limit: Int,
        @Parameter(name = "name", description = "Search by name", example = "lorem")
        @RequestParam(required = false)
        name: String?,
        @Parameter(name = "email", description = "Search by email", example = "lorem@lorem.com")
        @RequestParam(required = false)
        email: String?
    ): PaginationResponse<User> =
        userService.findAllUsers(email = email, name = name, offset = offset, limit = limit)
            .run messagePage@{
                PaginationResponse(offset = offset, limit = limit, total = this@messagePage.size, items = this@messagePage)
            }

    /**
     * Generate UserWrapperResponse with given UserResponse
     * @param user -- User entity that contains user data
     * @return UserWrapperResponse
     */
    private fun generateUserWrapperResponse(user: User): UserWrapperResponse {
        val roles: List<String> = user.roles.stream().map { r: Role -> RoleName.fromString(r = r.name!!.name)!!.name }.toList()
        val jwtToken: String = tokenProvider.generateJwtToken(email = user.email!!, roles = roles)
        return UserWrapperResponse(user = user, token = jwtToken)
            .also { log.info("UserWrapperResponse is generated. UserWrapperResponse: $it") }
    }
}
