package com.under.game.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.under.game.R
import com.under.game.core.library.ViewBindingDialog
import com.under.game.databinding.DialogPauseBinding
import com.under.game.ui.choice.FragmentChoiceDirections
import com.under.game.ui.game.CallbackViewModel

class DialogPause: ViewBindingDialog<DialogPauseBinding>(DialogPauseBinding::inflate) {
    private val viewModel: CallbackViewModel by activityViewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.Dialog_No_Border)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.setCancelable(false)
        dialog!!.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                viewModel.callback?.invoke()
                findNavController().popBackStack()
                true
            } else {
                false
            }
        }

        binding.playButton.setOnClickListener {
            viewModel.callback?.invoke()
            findNavController().popBackStack()
        }
    }
}