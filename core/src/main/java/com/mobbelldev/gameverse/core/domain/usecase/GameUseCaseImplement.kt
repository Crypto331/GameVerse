package com.mobbelldev.gameverse.core.domain.usecase

import com.mobbelldev.gameverse.core.utils.Resource
import com.mobbelldev.gameverse.core.domain.model.Game
import com.mobbelldev.gameverse.core.domain.repository.IGameRepository
import javax.inject.Inject

class GameUseCaseImplement @Inject constructor(private val iGameRepository: IGameRepository) :
    GameUseCase {
    override fun getGames() = iGameRepository.getGames()

    override fun getFavoriteGames() = iGameRepository.getFavoriteGames()

    override fun getDetailGameById(id: Int) =
        iGameRepository.getDetailGameById(id)

    override fun setFavoriteGame(game: Game) = iGameRepository.setFavoriteGame(game)

    override suspend fun searchGames(query: String): Resource<List<Game>> =
        iGameRepository.searchGames(query)

    override suspend fun insertGame(game: Game) = iGameRepository.insertGame(game)

}