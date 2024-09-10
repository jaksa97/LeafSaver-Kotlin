package com.github.jaksa97.LeafSaver_Kotlin.models.entities

import com.github.jaksa97.LeafSaver_Kotlin.models.auth.Token
import com.github.jaksa97.LeafSaver_Kotlin.models.enumClasses.UserRoles
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.NoArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
data class UserEntity(
    @Id
    @Autowired
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,

    @Column(nullable = false, name = "first_name")
    var firstName: String,

    @Column(nullable = false, name = "last_name")
    var lastName: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    private val password: String,

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    var role: UserRoles,

    @OneToMany(mappedBy = "user")
    var tokens: List<Token> = emptyList()

): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return email
    }
}
