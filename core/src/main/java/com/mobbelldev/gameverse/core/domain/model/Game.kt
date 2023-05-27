package com.mobbelldev.gameverse.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    val id: Int,
    val name: String,
    var description: String,
    val released: String,
    val bgImage: String,
    val metaScore: Int,
    val isFavorite: Boolean
) : Parcelable