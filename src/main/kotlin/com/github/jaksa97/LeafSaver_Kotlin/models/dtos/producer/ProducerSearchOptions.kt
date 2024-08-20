package com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.domain.Sort

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
data class ProducerSearchOptions(
    val page: Int,
    val pageSize: Int,
    val sortBy: SortByField,
    val sortDirection: Sort.Direction,
    val name: String
) {
    enum class SortByField {
        NAME
    }
}
