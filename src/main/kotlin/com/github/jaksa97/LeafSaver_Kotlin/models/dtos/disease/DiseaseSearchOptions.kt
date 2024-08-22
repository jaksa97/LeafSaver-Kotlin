package com.github.jaksa97.LeafSaver_Kotlin.models.dtos.disease

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.domain.Sort

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
data class DiseaseSearchOptions(
    val page: Int,
    val pageSize: Int,
    val sortBy: SortByField,
    val sortDirection: Sort.Direction,
    val name: String,
    val niceName: String
) {
    enum class SortByField {
        NAME,
        NICE_NAME
    }
}
