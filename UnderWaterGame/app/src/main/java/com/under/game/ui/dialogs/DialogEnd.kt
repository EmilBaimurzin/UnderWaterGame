package com.under.game.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.under.game.R
import com.under.game.core.library.ViewBindingDialog
import com.under.game.databinding.DialogEndBinding
import com.under.game.ui.choice.FragmentChoiceDirections

class DialogEnd : ViewBindingDialog<DialogEndBinding>(DialogEndBinding::inflate) {
    private val args: DialogEndArgs by navArgs()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.Dialog_No_Border)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.setCancelable(false)
        dialog!!.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                findNavController().popBackStack(R.id.fragmentMain, false, false)
                true
            } else {
                false
            }
        }

        binding.buttonRestart.setOnClickListener {
            findNavController().popBackStack(R.id.fragmentMain, false, false)
            findNavController().navigate(R.id.action_fragmentMain_to_fragmentChoice)
            findNavController().navigate(
                FragmentChoiceDirections.actionFragmentChoiceToFragmentGame(
                    args.difficulty,
                    args.defaultTime
                )
            )
        }

        binding.buttonClose.setOnClickListener {
            findNavController().popBackStack(R.id.fragmentMain, false, false)
        }

        val minutes = (args.time % 3600) / 60
        val seconds = args.time % 60

        binding.timeTextView.text = String.format("%02d:%02d", minutes, seconds)

        if (args.isWin) {
            binding.container.setBackgroundResource(R.drawable.bg_win)
        } else {
            binding.timeTextView.isVisible = false
            binding.container.setBackgroundResource(R.drawable.bg_lose)
        }
    }
}