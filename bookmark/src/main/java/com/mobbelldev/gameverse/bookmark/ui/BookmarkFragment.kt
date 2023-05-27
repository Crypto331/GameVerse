package com.mobbelldev.gameverse.bookmark.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mobbelldev.gameverse.bookmark.ViewModelFactory
import com.mobbelldev.gameverse.bookmark.databinding.FragmentBookmarkBinding
import com.mobbelldev.gameverse.bookmark.di.dependencies.DaggerBookmarkComponent
import com.mobbelldev.gameverse.core.domain.model.Game
import com.mobbelldev.gameverse.core.ui.GameAdapter
import com.mobbelldev.gameverse.di.BookmarkModuleDependencies
import com.mobbelldev.gameverse.util.NavigationUtils
import com.mobbelldev.gameverse.util.RecycleViewUtil
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject


class BookmarkFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val bookmarkViewModel: BookmarkViewModel by viewModels { factory }

    private val binding: FragmentBookmarkBinding by lazy {
        FragmentBookmarkBinding.inflate(layoutInflater)
    }

    private val gameAdapter: GameAdapter by lazy {
        GameAdapter().apply {
            setOnItemClickCallback(object : GameAdapter.OnItemClickCallback {
                override fun onItemSelected(game: Game) {
                    NavigationUtils.showSelectedData(context, game)
                }
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val bookmarkComponent = DaggerBookmarkComponent.builder()
            .context(context)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    context.applicationContext,
                    BookmarkModuleDependencies::class.java
                )
            )
            .build()
        bookmarkComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            bookmarkViewModel.bookmarkGames.observe(viewLifecycleOwner) { bookmarkData ->
                val isEmpty = bookmarkData.isEmpty()
                binding.apply {
                    lottieBookmark.isVisible = isEmpty
                    tvEmptyBookmark.isVisible = isEmpty
                    gameAdapter.submitList(bookmarkData)
                }
            }
            RecycleViewUtil.setupRecyclerView(
                binding.recycleViewBookmark,
                adapter = gameAdapter,
                requireContext()
            )
        }
    }
}