package com.mobbelldev.gameverse.explore

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mobbelldev.gameverse.R
import com.mobbelldev.gameverse.core.domain.model.Game
import com.mobbelldev.gameverse.core.ui.GameAdapter
import com.mobbelldev.gameverse.core.utils.Resource
import com.mobbelldev.gameverse.databinding.FragmentExploreBinding
import com.mobbelldev.gameverse.util.NavigationUtils
import com.mobbelldev.gameverse.util.RecycleViewUtil
import com.mobbelldev.gameverse.util.ViewUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExploreFragment : Fragment() {
    private val exploreViewModel: ExploreViewModel by viewModels()

    private val binding: FragmentExploreBinding by lazy {
        FragmentExploreBinding.inflate(layoutInflater)
    }

    private val gameAdapter: GameAdapter by lazy {
        GameAdapter().apply {
            setOnItemClickCallback(object : GameAdapter.OnItemClickCallback {
                override fun onItemSelected(game: Game) {
                    NavigationUtils.showSelectedData(
                        context,
                        game,
                        exploreViewModel,
                        viewLifecycleOwner
                    )
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        @Suppress("DEPRECATION")
        setHasOptionsMenu(true)
        setupRecycleView()
        ViewUtils.setVisibility(
            binding,
            R.id.tv_waiting to true,
            R.id.lottie_waiting_search to true,
            R.id.recycle_view_explore to false
        )

        exploreViewModel.games.observe(viewLifecycleOwner) { gameData ->
            when (gameData) {
                is Resource.Loading -> {
                    ViewUtils.setVisibility(
                        binding,
                        R.id.progressbar to true,
                        R.id.recycle_view_explore to false
                    )
                }

                is Resource.Success -> {
                    ViewUtils.setVisibility(
                        binding,
                        R.id.progressbar to false,
                        R.id.recycle_view_explore to true,
                        R.id.lottie_waiting_search to false,
                        R.id.tv_waiting to false
                    )
                    gameAdapter.submitList(gameData.data)
                }

                is Resource.Error -> {
                    ViewUtils.setVisibility(
                        binding,
                        R.id.progressbar to false,
                        R.id.recycle_view_explore to false,
                        R.id.lottie_not_found to true,
                        R.id.tv_waiting to false,
                        R.id.lottie_waiting_search to false
                    )
                    Toast.makeText(context, "Error: ${gameData.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupRecycleView() {
        RecycleViewUtil.setupRecyclerView(
            binding.recycleViewExplore,
            adapter = gameAdapter,
            requireContext()
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.icon_search, menu)

        val searchManager =
            requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.app_bar_search)
        val searchView = searchItem?.actionView as? SearchView

        searchView?.apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            queryHint = resources.getString(R.string.hint_search)

            setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    lifecycleScope.launch {
                        ViewUtils.setVisibility(
                            binding,
                            R.id.progressbar to true,
                            R.id.recycle_view_explore to false,
                            R.id.tv_waiting to false,
                            R.id.lottie_waiting_search to false,
                            R.id.lottie_not_found to false
                        )
                        exploreViewModel.searchGames(query)
                        clearFocus()
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false

            })
        }
    }
}