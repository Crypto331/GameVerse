package com.mobbelldev.gameverse.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobbelldev.gameverse.core.R
import com.mobbelldev.gameverse.core.databinding.ItemGameBinding
import com.mobbelldev.gameverse.core.domain.model.Game
import com.mobbelldev.gameverse.core.utils.DiffUtilHelper
import com.mobbelldev.gameverse.core.utils.load

class GameAdapter : ListAdapter<Game, GameAdapter.GameViewHolder>(DiffUtilHelper.diffCallback()) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemSelected(game: Game)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class GameViewHolder(binding: ItemGameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Game) {
            val binding = ItemGameBinding.bind(itemView)
            with(binding) {
                ivImageGame.load(data.bgImage)
                tvTitle.text = data.name
                tvReleased.text = data.released
                tvMetaScore.text = data.metaScore.toString()

                val drawableResId = when {
                    data.metaScore < 49 -> R.drawable.round_star_bad_24
                    data.metaScore < 79 -> R.drawable.round_star_good_24
                    else -> R.drawable.round_star_excellent_24
                }
                linearLayoutMetaScore.background =
                    AppCompatResources.getDrawable(itemView.context, drawableResId)
            }
            itemView.setOnClickListener { onItemClickCallback.onItemSelected(data) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder =
        GameViewHolder(ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}