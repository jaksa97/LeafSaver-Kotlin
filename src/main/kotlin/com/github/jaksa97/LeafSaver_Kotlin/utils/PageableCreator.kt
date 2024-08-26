package com.github.jaksa97.LeafSaver_Kotlin.utils

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

object PageableCreator {

    fun createPageable(pageNo: Int?, pageSize: Int?, sortBy: String?, direction: Sort.Direction?): PageRequest {

        val page = pageNo ?: 0
        val size = pageSize ?: 20
        val sortByField = sortBy ?: "id"
        val sortDirection = direction ?: Sort.Direction.ASC

        return PageRequest.of(page, size, Sort.by(sortDirection, sortByField))
    }
}