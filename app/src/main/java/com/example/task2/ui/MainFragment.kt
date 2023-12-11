package com.example.task2.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task2.R
import com.example.task2.ui.viewmodels.MainViewModel
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {


    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val adapter = MainAdapter(listOf()) {
            navController.navigate(
                R.id.action_main_to_anime_fragment, AnimeFragment.prepareBundle(
                    it.id,
                    it.images.jpg.url,
                    it.title,
                    it.synopsis,
                    it.score,
                )
            )
        }
        val progressView: ProgressBar = view.findViewById(R.id.progressBar)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler)
        val loadMoreButton: Button = view.findViewById(R.id.loadMoreButton)
        recyclerView.layoutManager = GridLayoutManager(view.context, 2)
        recyclerView.adapter = adapter
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                adapter.dataSet = state.animeList
                adapter.notifyDataSetChanged()
                progressView.isVisible = state.isLoading
                loadMoreButton.isVisible = !state.isLoading
            }
        }
        viewModel.fetchCurrentSeasonAnimes()
        loadMoreButton.setOnClickListener { viewModel.fetchCurrentSeasonAnimes() }
    }
}