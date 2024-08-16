package com.github.jaksa97.LeafSaver_Kotlin.controllers.producer

import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.services.ProducerService
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.RestController


@RestController
@RequiredArgsConstructor
class ProducerRestControllerImpl: ProducerRestController {

    private val _producerService: ProducerService? = null

    override fun getProducers(): Page<ProducerDto> {
        TODO("Not yet implemented")
    }

    override fun getProducerById(id: Int): ProducerDto {
        TODO("Not yet implemented")
    }

    override fun saveProducer(producerSaveDto: ProducerSaveDto): ProducerDto {
        TODO("Not yet implemented")
    }

    override fun updateProducer(id: Int, producerSaveDto: ProducerSaveDto): ProducerDto {
        TODO("Not yet implemented")
    }

    override fun deleteProducer(id: Int) {
        TODO("Not yet implemented")
    }
}