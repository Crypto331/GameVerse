package com.mobbelldev.gameverse.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mobbelldev.gameverse.core.domain.model.Game
import com.mobbelldev.gameverse.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val gameUseCase: GameUseCase) : ViewModel() {
    fun getDetailGame(id: Int) = gameUseCase.getDetailGameById(id).asLiveData()
    fun setBookmarkGame(game: Game) = gameUseCase.setFavoriteGame(game)
}