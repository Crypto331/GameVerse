package com.mobbelldev.gameverse.bookmark.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mobbelldev.gameverse.core.domain.usecase.GameUseCase

class BookmarkViewModel(gameUseCase: GameUseCase) : ViewModel() {
    val bookmarkGames = gameUseCase.getFavoriteGames().asLiveData()
}