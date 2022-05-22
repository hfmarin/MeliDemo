package com.hfmarin.meliapp.network.model

import com.hfmarin.meliapp.domain.model.Item
import com.hfmarin.meliapp.domain.util.DomainMapper

class ItemDtoMapper(private val attributeDtoMapper: AttributeDtoMapper) : DomainMapper<ResultDto, Item> {
    override fun mapToDomainModel(model: ResultDto): Item {
        return with(model) {
            Item(
                attributes = attributeDtoMapper.toDomainList(model.attributes),
                id = id,
                price = price,
                thumbnail = thumbnail,
                thumbnail_id = thumbnail_id,
                title = title,
                use_thumbnail_id = use_thumbnail_id,
                currency_id = currency_id,
            )
        }
    }

    override fun mapFromDomainModel(domainModel: Item): ResultDto {
        return with(domainModel) {
            ResultDto(
                attributes = attributeDtoMapper.fromDomainList(domainModel.attributes),
                id = id,
                price = price,
                thumbnail = thumbnail,
                thumbnail_id = thumbnail_id,
                title = title,
                use_thumbnail_id = use_thumbnail_id,
                currency_id = currency_id
            )
        }
    }

    fun toDomainList(initial: List<ResultDto>): List<Item> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Item>): List<ResultDto> {
        return initial.map { mapFromDomainModel(it) }
    }
}