package com.mobbelldev.gameverse.detail

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import com.mobbelldev.gameverse.R
import com.mobbelldev.gameverse.core.domain.model.Game
import com.mobbelldev.gameverse.core.utils.Resource
import com.mobbelldev.gameverse.core.utils.load
import com.mobbelldev.gameverse.databinding.ActivityDetailBinding
import com.mobbelldev.gameverse.util.ViewUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar()

        val gameId = intent.getIntExtra(EXTRA_DETAIL_GAME, -1)
        if (gameId != -1) {
            detailViewModel.getDetailGame(gameId).observe(this) { gameData ->
                when (gameData) {
                    is Resource.Loading -> ViewUtils.setVisibility(
                        binding,
                        R.id.progressbar to true
                    )

                    is Resource.Success -> {
                        val data = gameData.data as Game
                        setBookmarkState(data.isFavorite)
                        populateData(data)
                        ViewUtils.setVisibility(
                            binding,
                            R.id.progressbar to false,
                            R.id.app_bar_layout to true,
                            R.id.nested_scroll_view to true,
                            R.id.fab_add_game_to_bookmark to true
                        )
                        binding.fabAddGameToBookmark.setOnClickListener {
                            detailViewModel.setBookmarkGame(data)
                            setBookmarkState(data.isFavorite)
                            val toastMessage = when (!data.isFavorite) {
                                true -> R.string.text_added
                                false -> R.string.text_removed
                            }
                            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
                        }
                    }

                    is Resource.Error -> {
                        ViewUtils.setVisibility(
                            binding,
                            R.id.progressbar to false,
                            R.id.app_bar_layout to false,
                            R.id.nested_scroll_view to false,
                            R.id.fab_add_game_to_bookmark to false
                        )
                        Toast.makeText(this, "Error: ${gameData.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.appBarLayout.addOnOffsetChangedListener(this)
    }

    private fun setBookmarkState(isBookmark: Boolean) {
        val bookmarkDrawableResId =
            if (isBookmark) R.drawable.round_bookmark_24 else R.drawable.round_bookmark_border_24
        binding.fabAddGameToBookmark.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                bookmarkDrawableResId
            )
        )
    }

    private fun populateData(game: Game) {
        binding.apply {
            collapsingToolbarLayout.title = game.name
            ivImageGame.load(game.bgImage)
            tvDescription.text = game.description
        }
    }

    private var isImageHidden = false
    private val percentageToShowImage = 20
    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        val totalScrollRange = appBarLayout?.totalScrollRange ?: 0
        val currentScrollPercentage = (kotlin.math.abs(verticalOffset) * 100 / totalScrollRange)
        isImageHidden = currentScrollPercentage >= percentageToShowImage
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_DETAIL_GAME = "extra_detail_game"
    }
}