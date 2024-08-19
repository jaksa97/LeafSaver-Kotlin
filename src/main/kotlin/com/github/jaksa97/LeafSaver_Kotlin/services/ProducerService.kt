package com.github.jaksa97.LeafSaver_Kotlin.services

import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerSaveDto
import com.github.jaksa97.LeafSaver_Kotlin.models.dtos.producer.ProducerSearchOptions
import com.github.jaksa97.LeafSaver_Kotlin.models.mappers.ProducerMapper
import com.github.jaksa97.LeafSaver_Kotlin.repositories.ProducerRepository
import com.github.jaksa97.LeafSaver_Kotlin.repositories.search_specification.ProducerSearchSpecification
import lombok.RequiredArgsConstructor
import org.antlr.v4.runtime.atn.ErrorInfo
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service


@Service
@RequiredArgsConstructor
class ProducerService(
    private val _producerRepository: ProducerRepository,
    private val _producerMapper: ProducerMapper
) {

    fun getOne(id: Int): ProducerDto {
        val producerEntity = _producerRepository.findById(id).orElseThrow()

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

    fun save(producerSaveDto: ProducerSaveDto): ProducerDto {
        if (_producerRepository.findByName(producerSaveDto.name).isPresent) {
            throw Exception()
        }

        return _producerMapper.toDto(_producerRepository.save(_producerMapper.toEntity(producerSaveDto)))
    }

    fun update(id: Int, updateProducer: ProducerSaveDto): ProducerDto {
        val originalProducerEntity = _producerRepository.findById(id).orElseThrow()

        if (originalProducerEntity.name != updateProducer.name && _producerRepository.findByName(updateProducer.name).isPresent) {
            throw Exception()
        }

        val producerEntity = _producerMapper.toEntity(updateProducer)
        producerEntity.id = id

        _producerRepository.save(producerEntity)

        return _producerMapper.toDto(producerEntity)
    }

    fun remove(id: Int) {
        if (!_producerRepository.existsById(id)) {
            throw Exception()
        }

        _producerRepository.deleteById(id)
    }
}