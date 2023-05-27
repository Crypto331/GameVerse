package com.mobbelldev.gameverse.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobbelldev.gameverse.core.utils.Resource
import com.mobbelldev.gameverse.core.domain.model.Game
import com.mobbelldev.gameverse.core.domain.usecase.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(private val gameUseCase: GameUseCase) : ViewModel() {
    private var _games = MutableLiveData<Resource<List<Game>>>()
    val games: LiveData<Resource<List<Game>>> get() = _games

    suspend fun searchGames(query: String) {
        gameUseCase.searchGames(query)?.let { result ->
            _games.value = result
        }
    }

    suspend fun insertGame(game: Game) = gameUseCase.insertGame(game)
}