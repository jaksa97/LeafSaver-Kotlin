package com.github.jaksa97.LeafSaver_Kotlin.models.entities


import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.NoArgsConstructor

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
data class ProducerEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,

    @Column(nullable = false, name = "name", unique = true)
    var name: String
)
