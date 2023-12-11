package com.example.task2.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.task2.R
import com.example.task2.ui.states.BaseAnimeData
import com.example.task2.ui.viewmodels.AnimeViewModel
import kotlinx.coroutines.launch

private const val ID = "ID"
private const val IMAGE = "IMAGE"
private const val TITLE = "TITLE"
private const val DESCRIPTION = "DESCRIPTION"
private const val SCORE = "SCORE"

class AnimeFragment : Fragment(R.layout.fragment_anime) {
    private var id: Int? = null
    private var image: String? = null
    private var title: String? = null
    private var description: String? = null
    private var score: Float? = null

    private val viewModel: AnimeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ID)
            image = it.getString(IMAGE)
            title = it.getString(TITLE)
            description = it.getString(DESCRIPTION)
            score = it.getFloat(SCORE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView = view.findViewById<ImageView>(R.id.animeImageView)
        val titleTextView = view.findViewById<TextView>(R.id.animeTitle)
        val scoreTextView = view.findViewById<TextView>(R.id.animeScore)
        val descriptionTextView = view.findViewById<TextView>(R.id.animeDescription)
        val animeYear = view.findViewById<TextView>(R.id.animeYear)
        val toolbarTitle = view.findViewById<TextView>(R.id.toolbar_title)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                progressBar.isVisible = state.isLoading
                if (state.anime != null) {
                    toolbarTitle.text = state.anime.title
                    titleTextView.text = state.anime.title
                    scoreTextView.text = "Score: ${state.anime.score?.toString() ?: "No Score"}"
                    descriptionTextView.text = state.anime.synopsis
                    animeYear.text = "Year: ${state.anime.year ?: "Unknown"}"
                    state.anime.images.jpg.largeUrl.let {
                        Glide
                            .with(imageView)
                            .load(it)
                            .centerCrop()
                            .into(imageView)
                    }
                } else {
                    toolbarTitle.text = state.baseAnimeData.title ?: "Unknown title"
                    titleTextView.text = state.baseAnimeData.title ?: "Unknown title"
                    scoreTextView.text = "Score: ${state.baseAnimeData.score?.toString() ?: "No Score"}"
                    descriptionTextView.text = state.baseAnimeData.description ?: "No description"
                    state.baseAnimeData.image?.let {
                        Glide
                            .with(imageView)
                            .load(it)
                            .centerCrop()
                            .into(imageView)
                    }
                }
            }
        }
        viewModel.setBaseData(
            BaseAnimeData(
                id, title, description, image, score
            )
        )
        viewModel.fetchAnime()
    }

    companion object {
        @JvmStatic
        fun prepareBundle(id: Int, image: String, title: String, description: String?, score: Float?) =
            Bundle().apply {
                putInt(ID, id)
                putString(IMAGE, image)
                putString(TITLE, title)
                putString(DESCRIPTION, description)
                if (score != null) putFloat(SCORE, score)

            }
    }
}