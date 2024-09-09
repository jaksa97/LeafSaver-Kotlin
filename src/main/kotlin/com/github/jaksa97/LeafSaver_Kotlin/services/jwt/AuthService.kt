package com.github.jaksa97.LeafSaver_Kotlin.services.jwt

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ErrorInfo
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.models.auth.AuthResponse
import com.github.jaksa97.LeafSaver_Kotlin.models.auth.LoginRequest
import com.github.jaksa97.LeafSaver_Kotlin.models.auth.RegisterRequest
import com.github.jaksa97.LeafSaver_Kotlin.models.auth.Token
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.user.UserSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.models.entities.UserEntity
import com.github.jaksa97.LeafSaver_Kotlin.models.mappers.UserMapper
import com.github.jaksa97.LeafSaver_Kotlin.repositories.TokenRepository
import com.github.jaksa97.LeafSaver_Kotlin.repositories.UserRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val _userRepository: UserRepository,
    private val _userMapper: UserMapper,
    private val _passwordEncoder: PasswordEncoder,
    private val _jwtService: JwtService,
    private val _authManager: AuthenticationManager,
    private val _tokenRepository: TokenRepository
) {

    fun register(registerRequest: RegisterRequest): AuthResponse {
        val userToRegister = UserSaveDto(
            firstName = registerRequest.firstName,
            lastName = registerRequest.lastName,
            email = registerRequest.email,
            password = _passwordEncoder.encode(registerRequest.password),
            role = registerRequest.role
        )

        val user = _userRepository.save(_userMapper.toEntity(userToRegister))

        val accessToken = _jwtService.generateAccessToken(user)
        val refreshToken = _jwtService.generateRefreshToken(user)

        saveUserToken(accessToken, refreshToken, user)

        return AuthResponse(accessToken, refreshToken)
    }

    @Throws(ResourceNotFoundException::class)
    fun login(loginRequest: LoginRequest): AuthResponse {
        _authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.email,
                loginRequest.password
            )
        )

        val user = _userRepository.findByEmail(loginRequest.email).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.USER)
        }

        val accessToken = _jwtService.generateAccessToken(user)
        val refreshToken = _jwtService.generateRefreshToken(user)

        revokeAllTokensByUser(user)

        saveUserToken(accessToken, refreshToken, user)

        return AuthResponse(accessToken, refreshToken)
    }

    private fun saveUserToken(accessToken: String, refreshToken: String, user: UserEntity) {
        val token = Token(
            accessToken = accessToken,
            refreshToken = refreshToken,
            loggedOut = false,
            user = user
        )

        _tokenRepository.save(token)
    }

    private fun revokeAllTokensByUser(user: UserEntity) {
        val validTokenListByUser = _tokenRepository.findAllAccessTokenByUser(user.id)

        if (validTokenListByUser.isNotEmpty()) {
            validTokenListByUser.forEach {
                it.loggedOut = true
            }
        }

        _tokenRepository.saveAll(validTokenListByUser)
    }


    //Refactor to pass refreshToken as method parameter
    @Throws(ResourceNotFoundException::class)
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Any> {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }

        val token = authHeader.substringAfter("Bearer ")

        val email = _jwtService.extractEmail(token)

        val user = _userRepository.findByEmail(email).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.USER)
        }

        if (_jwtService.isRefreshValid(token, user)) {
            val accessToken = _jwtService.generateAccessToken(user)
            val refreshToken = _jwtService.generateRefreshToken(user)

            revokeAllTokensByUser(user)

            saveUserToken(accessToken, refreshToken, user)

            return ResponseEntity(AuthResponse(accessToken, refreshToken), HttpStatus.OK)
        }

        return ResponseEntity(HttpStatus.UNAUTHORIZED)
    }
}