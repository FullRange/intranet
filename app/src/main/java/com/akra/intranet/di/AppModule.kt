package com.akra.intranet.di

import com.akra.intranet.BuildConfig
import com.akra.intranet.data.remote.IntranetApi
import com.akra.intranet.data.repository.LogsRepositoryImpl
import com.akra.intranet.domain.repository.LogsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideIntranetApi(): IntranetApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IntranetApi::class.java)
    }

    @Provides
    @Singleton
    fun provideLogsRepository(api: IntranetApi): LogsRepository {
        return LogsRepositoryImpl(api)
    }
}