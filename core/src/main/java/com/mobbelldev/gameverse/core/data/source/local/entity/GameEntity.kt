package com.mobbelldev.gameverse.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class GameEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "released")
    val released: String,

    @ColumnInfo(name = "bgImage")
    val bgImage: String?,

    @ColumnInfo(name = "metaScore")
    val metaScore: Int,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean
)