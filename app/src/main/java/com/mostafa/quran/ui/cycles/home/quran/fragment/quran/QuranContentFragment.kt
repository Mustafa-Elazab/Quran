package com.mostafa.quran.ui.cycles.home.quran.fragment.quran

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.mostafa.quran.R
import com.mostafa.quran.base.BaseFragment
import com.mostafa.quran.data.remote.response.NetworkResponse
import com.mostafa.quran.databinding.FragmentQuranContentBinding
import com.mostafa.quran.ui.cycles.home.quran.fragment.bottomSheet.MyBottomSheetFragment
import com.mostafa.quran.ui.cycles.home.quran.viewmodel.QuranViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class QuranContentFragment :
    BaseFragment<FragmentQuranContentBinding>(R.layout.fragment_quran_content), Player.Listener {

    private val exoPlayer by lazy { ExoPlayer.Builder(requireContext()).build() }
    private val viewModel: QuranViewModel by viewModels()
    override val defineBindingVariables: ((FragmentQuranContentBinding) -> Unit)?
        get() = { binding ->
            binding.lifecycleOwner = viewLifecycleOwner
        }

    val model by navArgs<QuranContentFragmentArgs>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            appBar.pageTitleTv.text = model.quranContentModel.name
            appBar.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            tvSurahContent.text = model.quranContentModel.content
            settingsBtn.setOnClickListener {
                val bottomSheetFragment = MyBottomSheetFragment()
                bottomSheetFragment.show(
                    activity?.supportFragmentManager!!,
                    "MyBottomSheetFragment"
                )
            }
        }

        viewModel.playAudio(model.quranContentModel.Number)
        collectFlows(listOf(::collectSurahState))


    }

    private suspend fun collectSurahState() {
        viewModel.quranAudioState.collectLatest {
            when (it) {
                is NetworkResponse.ApiError -> {}
                NetworkResponse.Loading -> {}
                is NetworkResponse.NetworkError -> {}
                is NetworkResponse.Success -> {
                    setupMediaAudio(it.body.audioFile?.audioUrl.toString())
                }

                is NetworkResponse.UnknownError -> {}
                else -> {

                }
            }
        }
    }

    private fun setupMediaAudio(audioUrl: String) {
        val dataSourceFactory = DefaultDataSourceFactory(requireContext(), Util.getUserAgent(requireContext(), "YourApp"))
        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(audioUrl))

        exoPlayer.setMediaSource(mediaSource)
        exoPlayer.prepare()
        binding.playerView.player = exoPlayer

        binding.playerView.setOnClickListener {

            val newMediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(audioUrl))
            exoPlayer.setMediaSource(newMediaSource)
            exoPlayer.prepare()
            exoPlayer.play()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }
}