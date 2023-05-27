package com.mobbelldev.gameverse.core.utils

import androidx.recyclerview.widget.DiffUtil
import com.mobbelldev.gameverse.core.domain.model.Game

object DiffUtilHelper {
    fun diffCallback() = object : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(
            oldItem: Game,
            newItem: Game
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Game,
            newItem: Game
        ): Boolean {
            return oldItem == newItem
        }
    }
}