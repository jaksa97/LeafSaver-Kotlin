package com.github.jaksa97.LeafSaver_Kotlin.repositories.search_specification

import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerSearchOptions
import com.github.jaksa97.LeafSaver_Kotlin.models.entities.ProducerEntity
import com.github.jaksa97.LeafSaver_Kotlin.models.entities.ProducerEntity_
import jakarta.persistence.criteria.*
import lombok.RequiredArgsConstructor
import org.springframework.data.jpa.domain.Specification
import org.springframework.lang.Nullable


@RequiredArgsConstructor
class ProducerSearchSpecification(
    private val producerSearchOptions: ProducerSearchOptions
) : Specification<ProducerEntity> {

    override fun toPredicate(
        root: Root<ProducerEntity>,
        @Nullable query: CriteriaQuery<*>?,
        criteriaBuilder: CriteriaBuilder
    ): Predicate? {
        val predicates = mutableListOf<Predicate>()

        val name: Path<String> = root.get(ProducerEntity_.name)

        producerSearchOptions.sortBy.let { sortBy ->
            val propertyToSortBy = when (sortBy) {
                ProducerSearchOptions.SortByField.NAME -> name
            }

            val direction = producerSearchOptions.sortDirection
            if (direction.isAscending) {
                query?.orderBy(criteriaBuilder.asc(propertyToSortBy))
            } else {
                query?.orderBy(criteriaBuilder.desc(propertyToSortBy))
            }
        }

        producerSearchOptions.name.let { nameFilter ->
            predicates.add(
                criteriaBuilder.like(
                    criteriaBuilder.lower(name),
                    "%${nameFilter.lowercase()}%"
                )
            )
        }

        return criteriaBuilder.and(*predicates.toTypedArray())
    }
}