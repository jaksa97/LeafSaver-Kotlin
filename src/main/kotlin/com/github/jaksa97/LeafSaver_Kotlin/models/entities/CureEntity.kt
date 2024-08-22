package com.github.jaksa97.LeafSaver_Kotlin.models.entities

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.NoArgsConstructor

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cure")
data class CureEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    var drug: DrugEntity,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    var disease: DiseaseEntity,

    @Column(length = 1023, name = "instruction", nullable = false)
    var instruction: String
)
