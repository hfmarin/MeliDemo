package com.hfmarin.meliapp.di

import com.hfmarin.meliapp.network.MeliService
import com.hfmarin.meliapp.network.model.ItemDtoMapper
import com.hfmarin.meliapp.repository.MeliRepository
import com.hfmarin.meliapp.repository.MeliRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Singleton
    @Provides
    fun provideMeliRepository(
        meliService: MeliService,
        itemDtoMapper: ItemDtoMapper
    ): MeliRepository {
        return MeliRepositoryImpl(meliService, itemDtoMapper)
    }
}