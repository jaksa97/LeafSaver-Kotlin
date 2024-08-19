package com.github.jaksa97.LeafSaver_Kotlin.controllers.producer

import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerSearchOptions
import com.github.jaksa97.LeafSaver_Kotlin.services.ProducerService
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.RestController


@RestController
@RequiredArgsConstructor
class ProducerRestControllerImpl(
    private val _producerService: ProducerService
) : ProducerRestController {
//    override fun getProducers(producerSearchOptions: ProducerSearchOptions?): Page<ProducerDto> {
//        return _producerService.getAll(producerSearchOptions)
//    }

    override fun getProducers(): List<ProducerDto> {
        return _producerService.getAll()
    }

    override fun getProducerById(id: Int): ProducerDto {
        return _producerService.getOne(id)
    }

    override fun saveProducer(producerSaveDto: ProducerSaveDto): ProducerDto {
        return _producerService.save(producerSaveDto)
    }

    override fun updateProducer(id: Int, producerSaveDto: ProducerSaveDto): ProducerDto {
        return _producerService.update(id, producerSaveDto)
    }

    override fun deleteProducer(id: Int) {
        _producerService.remove(id)
    }
}