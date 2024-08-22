package com.github.jaksa97.LeafSaver_Kotlin.repositories.search_specification

import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.drug.DrugSearchOptions
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerSearchOptions
import com.github.jaksa97.LeafSaver_Kotlin.models.entities.DrugEntity
import com.github.jaksa97.LeafSaver_Kotlin.models.entities.DrugEntity_
import com.github.jaksa97.LeafSaver_Kotlin.models.entities.ProducerEntity_
import jakarta.persistence.criteria.*
import lombok.RequiredArgsConstructor
import org.springframework.data.jpa.domain.Specification

@RequiredArgsConstructor
class DrugSearchSpecification(
    private val drugSearchOptions: DrugSearchOptions
): Specification<DrugEntity> {

    override fun toPredicate(
        root: Root<DrugEntity>,
        query: CriteriaQuery<*>?,
        criteriaBuilder: CriteriaBuilder
    ): Predicate? {
        val predicates = mutableListOf<Predicate>()

        val name: Path<String> = root.get(DrugEntity_.name)

        drugSearchOptions.sortBy.let {
            val propertyToSortBy = when (it) {
                DrugSearchOptions.SortByField.NAME -> name
            }

            val direction = drugSearchOptions.sortDirection
            if (direction.isAscending) {
                query?.orderBy(criteriaBuilder.asc(propertyToSortBy))
            } else {
                query?.orderBy(criteriaBuilder.desc(propertyToSortBy))
            }
        }

        drugSearchOptions.name.let {
            predicates.add(
                criteriaBuilder.like(
                    criteriaBuilder.lower(name),
                    "%${it.lowercase()}%"
                )
            )
        }

        return criteriaBuilder.and(*predicates.toTypedArray())
    }
}