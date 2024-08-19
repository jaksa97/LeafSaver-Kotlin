package com.github.jaksa97.LeafSaver_Kotlin.services

import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ErrorInfo
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.ResourceNotFoundException
import com.github.jaksa97.LeafSaver_Kotlin.exceptions.UniqueViolationException
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.models.mappers.ProducerMapper
import com.github.jaksa97.LeafSaver_Kotlin.repositories.ProducerRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service


@Service
@RequiredArgsConstructor
class ProducerService(
    private val _producerRepository: ProducerRepository,
    private val _producerMapper: ProducerMapper
) {

    @Throws(ResourceNotFoundException::class)
    fun getOne(id: Int): ProducerDto{
        val producerEntity = _producerRepository.findById(id).orElseThrow {
            ResourceNotFoundException(ErrorInfo.ResourceType.PRODUCER)
        }

        return _producerMapper.toDto(producerEntity)
    }

//    fun getAll(producerSearchOptions: ProducerSearchOptions?): Page<ProducerDto> {
//        var page = 0
//
//        if (producerSearchOptions?.page != null && producerSearchOptions.page > 0) {
//            page = producerSearchOptions.page - 1
//        }
//
//        var pageSize = 10
//
//        if (producerSearchOptions?.pageSize != null && producerSearchOptions.pageSize >= 0) {
//            pageSize = producerSearchOptions.pageSize - 1
//        }
//
//        return _producerRepository.findAll(ProducerSearchSpecification(producerSearchOptions), PageRequest.of(page, pageSize)).map(_producerMapper::toDto)
//    }

    fun getAll(): List<ProducerDto> {
        return _producerRepository.findAll().map(_producerMapper::toDto)
    }

    @Throws(UniqueViolationException::class)
    fun save(producerSaveDto: ProducerSaveDto): ProducerDto {
        if (_producerRepository.findByName(producerSaveDto.name).isPresent) {
            throw UniqueViolationException(ErrorInfo.ResourceType.PRODUCER, "'name' already exists")
        }
        return _producerMapper.toDto(_producerRepository.save(_producerMapper.toEntity(producerSaveDto)))
    }

    @Throws(ResourceNotFoundException::class, UniqueViolationException::class)
    fun update(id: Int, updateProducer: ProducerSaveDto): ProducerDto {
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

    @Throws(ResourceNotFoundException::class)
    fun remove(id: Int) {
        if (!_producerRepository.existsById(id)) {
            throw ResourceNotFoundException(ErrorInfo.ResourceType.PRODUCER)
        }

        _producerRepository.deleteById(id)
    }
}