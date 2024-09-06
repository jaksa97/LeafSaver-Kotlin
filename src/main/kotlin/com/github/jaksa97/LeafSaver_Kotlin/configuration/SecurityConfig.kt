package com.github.jaksa97.LeafSaver_Kotlin.configuration

import com.github.jaksa97.LeafSaver_Kotlin.filters.JwtAuthFilter
import com.github.jaksa97.LeafSaver_Kotlin.services.jwt.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val _userDetailsService: CustomUserDetailsService,
    private val _jwtAuthFilter: JwtAuthFilter
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
                    "/swagger-ui/**",
                    "/api-docs/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .userDetailsService(_userDetailsService)
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(_jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()


    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    @Throws(Exception::class)
    fun authManager(config: AuthenticationConfiguration): AuthenticationManager = config.authenticationManager
}