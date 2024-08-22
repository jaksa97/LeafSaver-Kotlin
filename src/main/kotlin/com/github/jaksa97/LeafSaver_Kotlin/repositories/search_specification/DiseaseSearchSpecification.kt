package com.github.jaksa97.LeafSaver_Kotlin.repositories.search_specification

import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.disease.DiseaseSearchOptions
import com.github.jaksa97.LeafSaver_Kotlin.models.entities.DiseaseEntity
import com.github.jaksa97.LeafSaver_Kotlin.models.entities.DiseaseEntity_
import jakarta.persistence.criteria.*
import org.springframework.data.jpa.domain.Specification

class DiseaseSearchSpecification(
    private val diseaseSearchOptions: DiseaseSearchOptions
): Specification<DiseaseEntity> {

    override fun toPredicate(
        root: Root<DiseaseEntity>,
        query: CriteriaQuery<*>?,
        criteriaBuilder: CriteriaBuilder
    ): Predicate? {
        val predicates = mutableListOf<Predicate>()

        val name: Path<String> = root.get(DiseaseEntity_.name)
        val niceName: Path<String> = root.get(DiseaseEntity_.niceName)

        diseaseSearchOptions.sortBy.let {
            val propertyToSortBy = when(it) {
                DiseaseSearchOptions.SortByField.NAME -> name
                DiseaseSearchOptions.SortByField.NICE_NAME -> niceName
            }

            val direction = diseaseSearchOptions.sortDirection
            if (direction.isAscending) {
                query?.orderBy(criteriaBuilder.asc(propertyToSortBy))
            } else {
                query?.orderBy(criteriaBuilder.desc(propertyToSortBy))
            }
        }

        diseaseSearchOptions.name?.let {
            predicates.add(
                criteriaBuilder.like(
                    criteriaBuilder.lower(name),
                    "%${it.lowercase()}%"
                )
            )
        }

        diseaseSearchOptions.niceName?.let {
            predicates.add(
                criteriaBuilder.like(
                    criteriaBuilder.lower(niceName),
                    "%${it.lowercase()}%"
                )
            )
        }

        return criteriaBuilder.and(*predicates.toTypedArray())
    }

}