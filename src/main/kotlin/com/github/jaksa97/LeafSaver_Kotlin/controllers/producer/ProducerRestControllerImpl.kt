package com.github.jaksa97.LeafSaver_Kotlin.controllers.producer

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.UniqueViolationException
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.services.ProducerService
import com.github.jaksa97.LeafSaver_Kotlin.utils.PageableCreator
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.RestController


@RestController
@RequiredArgsConstructor
@Tag(name = "Producers")
class ProducerRestControllerImpl(
    private val _producerService: ProducerService
) : ProducerRestController {

    override fun getProducers(
        page: Int?,
        pageSize: Int?,
        sortBy: String?,
        sortDirection: Sort.Direction?
    ): Page<ProducerDto> {
        return _producerService.getAll(PageableCreator.createPageable(page, pageSize, sortBy, sortDirection))
    }

    @Throws(ResourceNotFoundException::class)
    override fun getProducerById(
        id: Int
    ): ProducerDto {
        return _producerService.getOne(id)
    }

    @Throws(UniqueViolationException::class)
    override fun saveProducer(
        producerSaveDto: ProducerSaveDto
    ): ProducerDto {
        return _producerService.save(producerSaveDto)
    }

    @Throws(ResourceNotFoundException::class, UniqueViolationException::class)
    override fun updateProducer(
        id: Int,
        producerSaveDto: ProducerSaveDto
    ): ProducerDto {
        return _producerService.update(id, producerSaveDto)
    }

    @Throws(ResourceNotFoundException::class)
    override fun deleteProducer(
        id: Int
    ) {
        _producerService.remove(id)
    }
}