package com.mobbelldev.gameverse.core.data.mapper

import com.mobbelldev.gameverse.core.data.source.local.entity.GameEntity
import com.mobbelldev.gameverse.core.data.source.remote.response.GameResponse
import com.mobbelldev.gameverse.core.domain.model.Game

object DataMapper {
    fun mapResponsesToEntities(gameResponse: List<GameResponse>): List<GameEntity> {
        return gameResponse.map {
            GameEntity(
                id = it.id,
                name = it.name,
                description = "",
                released = it.released ?: "No Data",
                bgImage = it.backgroundImage,
                metaScore = it.metacritic ?: 0,
                isFavorite = false
            )
        }
    }

    fun mapEntitiesToDomain(gameEntity: List<GameEntity>): List<Game> =
        gameEntity.map {
            Game(
                id = it.id,
                name = it.name,
                description = it.description,
                released = it.released,
                bgImage = it.bgImage.toString(),
                metaScore = it.metaScore,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(game: Game): GameEntity = GameEntity(
        id = game.id,
        name = game.name,
        description = game.description,
        released = game.released,
        bgImage = game.bgImage,
        metaScore = game.metaScore,
        isFavorite = game.isFavorite
    )

    fun mapEntityToDomain(gameEntity: GameEntity): Game = Game(
        id = gameEntity.id,
        name = gameEntity.name,
        description = gameEntity.description,
        released = gameEntity.released,
        bgImage = gameEntity.bgImage.toString(),
        metaScore = gameEntity.metaScore,
        isFavorite = gameEntity.isFavorite
    )

    fun mapResponseToEntity(gameResponse: GameResponse): List<GameEntity> =
        listOf(
            GameEntity(
                id = gameResponse.id,
                name = gameResponse.name,
                description = gameResponse.descriptionRaw,
                released = gameResponse.released ?: "No Data",
                bgImage = gameResponse.backgroundImage,
                metaScore = gameResponse.metacritic ?: 0,
                isFavorite = false
            )
        )

}