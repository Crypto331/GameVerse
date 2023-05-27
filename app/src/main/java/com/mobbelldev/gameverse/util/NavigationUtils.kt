package com.mobbelldev.gameverse.util

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.mobbelldev.gameverse.core.domain.model.Game
import com.mobbelldev.gameverse.detail.DetailActivity
import com.mobbelldev.gameverse.explore.ExploreViewModel
import kotlinx.coroutines.launch

object NavigationUtils {
    fun showSelectedData(
        context: Context?,
        game: Game,
        exploreViewModel: ExploreViewModel? = null,
        lifecycleOwner: LifecycleOwner? = null
    ) {
        val gameId = game.id
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DETAIL_GAME, gameId)
        lifecycleOwner?.lifecycleScope?.launch {
            exploreViewModel?.insertGame(game)
        }
        context?.startActivity(intent)
    }

}