package com.mobbelldev.gameverse.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mobbelldev.gameverse.R
import com.mobbelldev.gameverse.core.domain.model.Game
import com.mobbelldev.gameverse.core.ui.GameAdapter
import com.mobbelldev.gameverse.core.utils.Resource
import com.mobbelldev.gameverse.databinding.FragmentHomeBinding
import com.mobbelldev.gameverse.util.NavigationUtils
import com.mobbelldev.gameverse.util.RecycleViewUtil
import com.mobbelldev.gameverse.util.ViewUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by viewModels()

    private val gameAdapter: GameAdapter by lazy {
        GameAdapter().apply {
            setOnItemClickCallback(object : GameAdapter.OnItemClickCallback {
                override fun onItemSelected(game: Game) {
                    NavigationUtils.showSelectedData(requireActivity(), game)
                }

            })
        }
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            homeViewModel.game.observe(viewLifecycleOwner) { gameData ->
                if (gameData != null) {
                    when (gameData) {
                        is Resource.Loading -> ViewUtils.setVisibility(
                            binding,
                            R.id.progressbar to true
                        )

                        is Resource.Success -> {
                            ViewUtils.setVisibility(
                                binding,
                                R.id.progressbar to false,
                                R.id.recycle_view_game to true
                            )
                            gameAdapter.submitList(gameData.data)
                        }

                        is Resource.Error -> {
                            ViewUtils.setVisibility(
                                binding,
                                R.id.progressbar to true,
                                R.id.recycle_view_game to false
                            )
                            Toast.makeText(
                                context,
                                "Error: ${gameData.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            RecycleViewUtil.setupRecyclerView(
                binding.recycleViewGame,
                gameAdapter,
                requireActivity()
            )
        }
    }

    override fun onDestroyView() {
        binding.root.removeAllViews()
        _binding = null
        super.onDestroyView()
    }
}