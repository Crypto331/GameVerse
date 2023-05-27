package com.mobbelldev.gameverse.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mobbelldev.gameverse.core.domain.usecase.GameUseCaseImplement
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(gameUseCaseImplement: GameUseCaseImplement) : ViewModel() {
    val game = gameUseCaseImplement.getGames().asLiveData()
}