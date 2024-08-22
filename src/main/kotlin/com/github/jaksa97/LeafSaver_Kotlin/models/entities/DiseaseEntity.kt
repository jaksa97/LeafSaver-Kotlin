package com.github.jaksa97.LeafSaver_Kotlin.models.entities

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.NoArgsConstructor

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "disease")
data class DiseaseEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,

    @Column(length = 1023, nullable = false, name = "name", unique = true)
    var name: String,

    @Column(length = 1023, nullable = false, name= "nice_name")
    var niceName: String
)
