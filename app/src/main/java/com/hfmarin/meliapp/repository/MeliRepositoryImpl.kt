package com.hfmarin.meliapp.repository

import android.util.Log
import com.hfmarin.meliapp.domain.model.Item
import com.hfmarin.meliapp.network.MeliService
import com.hfmarin.meliapp.network.model.ItemDtoMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class MeliRepositoryImpl(
    private val meliService: MeliService,
    private val itemDtoMapper: ItemDtoMapper,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MeliRepository {

    override suspend fun search(query: String): Result<List<Item>> {
        return try {
            val remoteResult = meliService.search(query)
            (Result.success(itemDtoMapper.toDomainList(remoteResult.results)))
        } catch (ex: Exception) {
            Log.e(MeliRepository::class.simpleName, "search" + ex.message)
            Result.failure(ex)
        }

    }

    override suspend fun item(id: String): Result<Item> {
        return try {
            val remoteResult = meliService.item(id)
            (Result.success(itemDtoMapper.mapToDomainModel(remoteResult)))
        } catch (ex: Exception) {
            Log.e(MeliRepository::class.simpleName, "getItem" + ex.message)
            Result.failure(ex)
        }
    }

}