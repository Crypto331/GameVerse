package com.mobbelldev.gameverse.di

import com.mobbelldev.gameverse.core.domain.usecase.GameUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface BookmarkModuleDependencies {
    fun gameUseCase(): GameUseCase
}