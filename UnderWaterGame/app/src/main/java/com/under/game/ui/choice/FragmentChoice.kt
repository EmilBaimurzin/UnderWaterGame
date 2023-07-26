package com.under.game.ui.choice

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.under.game.core.library.shortToast
import com.under.game.databinding.FragmentChoiceBinding
import com.under.game.domain.other.AppPrefs
import com.under.game.domain.other.Difficulty
import com.under.game.ui.other.ViewBindingFragment

class FragmentChoice : ViewBindingFragment<FragmentChoiceBinding>(FragmentChoiceBinding::inflate) {
    private val sp by lazy {
        AppPrefs(requireContext())
    }
    private val viewModel: ChoiceViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDifficulties()
        setBalance()

        binding.apply {
            easyButtonLeft.setOnClickListener {
                viewModel.minusTime(Difficulty.EASY)
            }

            easyButtonRight.setOnClickListener {
                viewModel.plusTime(Difficulty.EASY)
            }

            normalButtonLeft.setOnClickListener {
                viewModel.minusTime(Difficulty.NORMAL)
            }

            normalButtonRight.setOnClickListener {
                viewModel.plusTime(Difficulty.NORMAL)
            }

            mediumButtonLeft.setOnClickListener {
                viewModel.minusTime(Difficulty.MEDIUM)
            }

            mediumButtonRight.setOnClickListener {
                viewModel.plusTime(Difficulty.MEDIUM)
            }

            hardButtonLeft.setOnClickListener {
                viewModel.minusTime(Difficulty.HARD)
            }

            hardButtonRight.setOnClickListener {
                viewModel.plusTime(Difficulty.HARD)
            }

            binding.normalBuyButton.setOnClickListener {
                if (sp.getBalance() >= 1000) {
                    sp.setBalance(-1000)
                    sp.buyDifficulty(Difficulty.NORMAL)
                    setBalance()
                    setDifficulties()
                } else {
                    shortToast(requireContext(), "Not enough money")
                }
            }
            binding.mediumBuyButton.setOnClickListener {
                if (sp.getBalance() >= 1000) {
                    sp.setBalance(-1000)
                    sp.buyDifficulty(Difficulty.MEDIUM)
                    setBalance()
                    setDifficulties()
                } else {
                    shortToast(requireContext(), "Not enough money")
                }
            }
            binding.hardBuyButton.setOnClickListener {
                if (sp.getBalance() >= 1000) {
                    sp.setBalance(-1000)
                    sp.buyDifficulty(Difficulty.HARD)
                    setBalance()
                    setDifficulties()
                } else {
                    shortToast(requireContext(), "Not enough money")
                }
            }

            binding.easyLayout.setOnClickListener {
                findNavController().navigate(
                    FragmentChoiceDirections.actionFragmentChoiceToFragmentGame(
                        Difficulty.EASY,
                        viewModel.easyTime.value!!
                    )
                )
            }

            binding.normalLayout.setOnClickListener {
                if (sp.isDifficultyBought(Difficulty.NORMAL)) {
                    findNavController().navigate(
                        FragmentChoiceDirections.actionFragmentChoiceToFragmentGame(
                            Difficulty.NORMAL,
                            viewModel.normalTime.value!!
                        )
                    )
                } else {
                    shortToast(requireContext(), "You must buy this difficulty before play it")
                }
            }

            binding.mediumLayout.setOnClickListener {
                if (sp.isDifficultyBought(Difficulty.MEDIUM)) {
                    findNavController().navigate(
                        FragmentChoiceDirections.actionFragmentChoiceToFragmentGame(
                            Difficulty.MEDIUM,
                            viewModel.mediumTime.value!!
                        )
                    )
                } else {
                    shortToast(requireContext(), "You must buy this difficulty before play it")
                }
            }

            binding.hardLayout.setOnClickListener {
                if (sp.isDifficultyBought(Difficulty.HARD)) {
                    findNavController().navigate(
                        FragmentChoiceDirections.actionFragmentChoiceToFragmentGame(
                            Difficulty.HARD,
                            viewModel.hardTime.value!!
                        )
                    )
                } else {
                    shortToast(requireContext(), "You must buy this difficulty before play it")
                }
            }

            binding.menuButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        viewModel.easyTime.observe(viewLifecycleOwner) {
            binding.easyTimeText.text = it.toString()
        }

        viewModel.normalTime.observe(viewLifecycleOwner) {
            binding.normalTimeText.text = it.toString()
        }

        viewModel.mediumTime.observe(viewLifecycleOwner) {
            binding.mediumTimeText.text = it.toString()
        }

        viewModel.hardTime.observe(viewLifecycleOwner) {
            binding.hardTimeText.text = it.toString()
        }
    }

    private fun setDifficulties() {
        binding.normalBuyButton.isVisible = !sp.isDifficultyBought(Difficulty.NORMAL)
        binding.normalTimeLayout.isVisible = sp.isDifficultyBought(Difficulty.NORMAL)

        binding.mediumBuyButton.isVisible = !sp.isDifficultyBought(Difficulty.MEDIUM)
        binding.mediumTimeLayout.isVisible = sp.isDifficultyBought(Difficulty.MEDIUM)

        binding.hardBuyButton.isVisible = !sp.isDifficultyBought(Difficulty.HARD)
        binding.hardTimeLayout.isVisible = sp.isDifficultyBought(Difficulty.HARD)
    }

    private fun setBalance() {
        binding.balanceTextView.text = sp.getBalance().toString()
    }
}