package com.github.jaksa97.LeafSaver_Kotlin.configuration

import com.github.jaksa97.LeafSaver_Kotlin.filters.JwtAuthFilter
import com.github.jaksa97.LeafSaver_Kotlin.services.jwt.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val _userDetailsService: CustomUserDetailsService,
    private val _jwtAuthFilter: JwtAuthFilter,
    private val _accessDeniedHandler: CustomAccessDeniedHandler,
    private val _logoutHandler: CustomLogoutHandler
) {

    @Bean
    @Throws(Exception::class)
    fun securityFilerChain(http: HttpSecurity): SecurityFilterChain =
        http
            .csrf {
                it.disable()
            }
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/auth/login",
                    "/auth/register",
                    "/auth/refreshToken",
                    "/swagger-ui/**",
                    "/api-docs/**",
                    "/h2-console/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .userDetailsService(_userDetailsService)
            .exceptionHandling {
                it.accessDeniedHandler(_accessDeniedHandler).authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(_jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .logout {
                it.logoutUrl("/auth/logout")
                    .addLogoutHandler(_logoutHandler)
                    .logoutSuccessHandler { _, _, _ ->
                        SecurityContextHolder.clearContext()
                    }
            }
            .build()


    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    @Throws(Exception::class)
    fun authManager(config: AuthenticationConfiguration): AuthenticationManager = config.authenticationManager
}