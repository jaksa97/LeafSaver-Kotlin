package com.github.jaksa97.LeafSaver_Kotlin.models.entities


import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
data class ProducerEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int,

    @Column(nullable = false, name = "name", unique = true)
    private val name: String
)
