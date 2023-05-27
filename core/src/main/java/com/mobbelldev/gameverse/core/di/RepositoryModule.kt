package com.mobbelldev.gameverse.core.di

import com.mobbelldev.gameverse.core.domain.repository.GameRepositoryImpl
import com.mobbelldev.gameverse.core.domain.repository.IGameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(gameRepositoryImpl: GameRepositoryImpl): IGameRepository
}