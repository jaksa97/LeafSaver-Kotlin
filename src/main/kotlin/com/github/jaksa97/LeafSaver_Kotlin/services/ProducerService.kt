package com.github.jaksa97.LeafSaver_Kotlin.services

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ErrorInfo
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.UniqueViolationException
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.models.mappers.ProducerMapper
import com.github.jaksa97.LeafSaver_Kotlin.repositories.DrugRepository
import com.github.jaksa97.LeafSaver_Kotlin.repositories.ProducerRepository
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service


@Service
@RequiredArgsConstructor
class ProducerService(
    private val _producerRepository: ProducerRepository,
    private val _producerMapper: ProducerMapper,
    private val _drugRepository: DrugRepository
) {

    @Throws(ResourceNotFoundException::class)
    fun getOne(
        id: Int
    ): ProducerDto {
        val producerEntity = _producerRepository.findById(id).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.PRODUCER)
        }

        return _producerMapper.toDto(producerEntity)
    }

    fun getAll(name: String?, pageable: PageRequest): Page<ProducerDto> = _producerRepository.findAll(name, pageable).map(_producerMapper::toDto)

    @Throws(UniqueViolationException::class)
    fun save(
        producerSaveDto: ProducerSaveDto
    ): ProducerDto {
        if (_producerRepository.findByName(producerSaveDto.name).isPresent) {
            throw UniqueViolationException(ErrorInfo.ResourceType.PRODUCER, "'name' already exists")
        }

        return _producerMapper.toDto(_producerRepository.save(_producerMapper.toEntity(producerSaveDto)))
    }

    @Throws(ResourceNotFoundException::class, UniqueViolationException::class)
    fun update(
        id: Int,
        updateProducer: ProducerSaveDto
    ): ProducerDto {
        val originalProducerEntity = _producerRepository.findById(id).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.PRODUCER)
        }

        if (originalProducerEntity.name != updateProducer.name && _producerRepository.findByName(updateProducer.name).isPresent) {
            throw UniqueViolationException(ErrorInfo.ResourceType.PRODUCER, "'name' already exists")
        }

        val producerEntity = _producerMapper.toEntity(updateProducer)
        producerEntity.id = id

        _producerRepository.save(producerEntity)

        return _producerMapper.toDto(producerEntity)
    }

    @Transactional
    @Throws(ResourceNotFoundException::class)
    fun remove(
        id: Int
    ) {
        val producerEntity = _producerRepository.findById(id).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.PRODUCER)
        }

        _drugRepository.deleteAllByProducer(producerEntity)

        _producerRepository.deleteById(id)
    }
}