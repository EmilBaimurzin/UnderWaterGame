package com.under.game.ui.game

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.under.game.R
import com.under.game.core.library.l
import com.under.game.databinding.FragmentGameBinding
import com.under.game.domain.game.GameAdapter
import com.under.game.domain.other.AppPrefs
import com.under.game.ui.other.ViewBindingFragment

class FragmentGame : ViewBindingFragment<FragmentGameBinding>(FragmentGameBinding::inflate) {
    private lateinit var gameAdapter: GameAdapter
    private lateinit var viewModel: GameViewModel
    private val callbackViewModel: CallbackViewModel by activityViewModels()
    private val args: FragmentGameArgs by navArgs()
    private val sp by lazy {
        AppPrefs(requireContext())
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initAdapter()
        setBalance()

        viewModel.winCallback = {
            end(true)
        }

        callbackViewModel.callback = {
            viewModel.pauseState = false
            viewModel.startTimer()
        }

        binding.pauseButton.setOnClickListener {
            viewModel.pauseState = true
            viewModel.stopTimer()
            findNavController().navigate(R.id.action_fragmentGame_to_dialogPause)
        }

        binding.menuButton.setOnClickListener {
            findNavController().popBackStack(R.id.fragmentMain, false, false)
        }

        viewModel.list.observe(viewLifecycleOwner) {
            l(it.toString())
            gameAdapter.items = it.toMutableList()
            gameAdapter.notifyDataSetChanged()
        }
        viewModel.timer.observe(viewLifecycleOwner) { totalSecs ->
            val minutes = (totalSecs % 3600) / 60;
            val seconds = totalSecs % 60;

            binding.timeTextView.text = String.format("%02d:%02d", minutes, seconds)
            if (viewModel.gameState && !viewModel.pauseState && totalSecs == 0) {
                end(false)
            }
        }

        if (viewModel.gameState && !viewModel.pauseState) {
            viewModel.startTimer()
        }

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            GameViewModelFactory(args.difficulty, args.time)
        )[GameViewModel::class.java]
    }

    private fun end(isWin: Boolean) {
        viewModel.stopTimer()
        viewModel.gameState = false
        if (isWin) {
            sp.setBalance(50)
        }
        setBalance()
        findNavController().navigate(
            FragmentGameDirections.actionFragmentGameToDialogEnd(
                args.time - viewModel.timer.value!!,
                args.difficulty,
                isWin,
                args.time
            )
        )
    }

    private fun setBalance() {
        binding.balanceTextView.text = sp.getBalance().toString()
    }

    private fun initAdapter() {
        gameAdapter = GameAdapter {
            if (viewModel.list.value!!.find { it.closeAnimation } == null && viewModel.list.value!!.find { it.openAnimation } == null) {
                viewModel.openItem(it)
            }
        }
        with(binding.gameRv) {
            adapter = gameAdapter
            layoutManager = GridLayoutManager(requireContext(), 3).also {
                it.orientation = GridLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
            itemAnimator = null
        }
    }
}