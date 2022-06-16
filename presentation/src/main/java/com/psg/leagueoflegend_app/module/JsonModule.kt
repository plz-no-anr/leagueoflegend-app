package com.psg.leagueoflegend_app.module

import android.content.Context
import com.psg.data.utils.JsonUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object JsonModule {
    @Provides
    @Singleton
    fun provideJsonService(@ApplicationContext context: Context): JsonUtils = JsonUtils(context)
}

