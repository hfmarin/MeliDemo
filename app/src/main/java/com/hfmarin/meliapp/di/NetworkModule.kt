package com.hfmarin.meliapp.di

import com.google.gson.GsonBuilder
import com.hfmarin.meliapp.network.MeliService
import com.hfmarin.meliapp.network.model.AttributeDtoMapper
import com.hfmarin.meliapp.network.model.ItemDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideItemMapper(attributeDtoMapper: AttributeDtoMapper): ItemDtoMapper {
        return ItemDtoMapper(attributeDtoMapper)
    }

    @Singleton
    @Provides
    fun provideAttributeMapper(): AttributeDtoMapper {
        return AttributeDtoMapper()
    }


    @Singleton
    @Provides
    fun provideMeliService(): MeliService {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(httpClient.build())
            .build()
            .create(MeliService::class.java)
    }

    @Singleton
    @Provides
    @Named("auth_token")
    fun provideAuthToken(): String {
        return "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    }
}