package com.github.jaksa97.LeafSaver_Kotlin.models.entities

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.NoArgsConstructor

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "drug")
data class DrugEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int,

    @Column(nullable = false, name = "name", unique = true)
    var name: String,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    var producer: ProducerEntity,

    @Column(length = 1023, nullable = false, name = "description")
    var description: String
)
