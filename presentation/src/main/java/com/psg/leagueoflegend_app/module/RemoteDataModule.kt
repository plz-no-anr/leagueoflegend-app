package com.psg.leagueoflegend_app.module

import com.psg.data.api.LeagueOfLegendAPI
import com.psg.data.api.RetrofitClient
import com.psg.data.repository.lol.remote.LolRemoteDataSource
import com.psg.data.repository.lol.remote.LolRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {
    @Provides
    @Singleton
    fun provideApiService(): LeagueOfLegendAPI =
        Retrofit.Builder()
            .baseUrl(RetrofitClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LeagueOfLegendAPI::class.java)

    @Provides
    @Singleton
    fun provideRemoteData(api: LeagueOfLegendAPI): LolRemoteDataSource = LolRemoteDataSourceImpl(api)
}

