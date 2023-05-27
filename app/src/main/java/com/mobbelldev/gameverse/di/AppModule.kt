package com.mobbelldev.gameverse.di

import com.mobbelldev.gameverse.core.domain.usecase.GameUseCase
import com.mobbelldev.gameverse.core.domain.usecase.GameUseCaseImplement
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun provideGameUseCase(gameUseCaseImplement: GameUseCaseImplement): GameUseCase
}