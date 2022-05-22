package com.hfmarin.meliapp.network.model

import com.hfmarin.meliapp.domain.model.Attribute
import com.hfmarin.meliapp.domain.util.DomainMapper

class AttributeDtoMapper: DomainMapper<AttributeDto, Attribute> {
    override fun mapToDomainModel(model: AttributeDto): Attribute {
        return Attribute(model.name, model.value ?: "Not Set")
    }

    override fun mapFromDomainModel(domainModel: Attribute): AttributeDto {
        return AttributeDto(domainModel.name, domainModel.value)
    }

    fun toDomainList(initial: List<AttributeDto>): List<Attribute> {
        return initial.map { mapToDomainModel(it) }
    }
    fun fromDomainList(initial:List<Attribute>): List<AttributeDto> {
        return initial.map { mapFromDomainModel(it) }
    }
}