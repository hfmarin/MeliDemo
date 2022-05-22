package com.hfmarin.meliapp.repository

import com.hfmarin.meliapp.domain.model.Item


interface MeliRepository {

    suspend fun search(query : String): Result<List<Item>>

    suspend fun item(id: String) : Result<Item>
}