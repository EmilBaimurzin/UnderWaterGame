package com.under.game.domain.game

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.RecyclerView
import com.under.game.R
import com.under.game.core.library.l
import com.under.game.databinding.ItemGameBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameAdapter(private var onItemClick: ((Int) -> Unit)? = null) : RecyclerView.Adapter<GameViewHolder>() {
    var items = mutableListOf<GameItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(
            ItemGameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(items[position])
        holder.onItemClick = onItemClick
    }
}

class GameViewHolder(private val binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root) {
    var onItemClick: ((Int) -> Unit)? = null
    fun bind(item: GameItem) {
        binding.apply {
            val img = when (item.value) {
                1 -> R.drawable.symbol01
                2 -> R.drawable.symbol02
                3 -> R.drawable.symbol03
                4 -> R.drawable.symbol04
                5 -> R.drawable.symbol05
                else -> R.drawable.symbol06
            }
            if (item.isOpened) {
                itemImg.setImageResource(img)
                binding.root.setBackgroundResource(R.drawable.bg_card_opened)
            } else {
                itemImg.setImageDrawable(null)
                binding.root.setBackgroundResource(R.drawable.bg_card_closed)
            }
            if (item.openAnimation) {
                flipImage(binding.root, img)
            }

            if (item.closeAnimation) {
                flipImage(binding.root, null)
            }

            binding.root.setOnClickListener {
                if (!item.openAnimation && !item.closeAnimation && !item.isOpened) {
                    onItemClick?.invoke(adapterPosition)
                }
            }
        }
    }

    private fun flipImage(
        view: View,
        @DrawableRes img: Int?,
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            delay(200)
            if (img != null) {
                binding.root.setBackgroundResource(R.drawable.bg_card_opened)
                binding.itemImg.setImageResource(img)
            } else {
                binding.root.setBackgroundResource(R.drawable.bg_card_closed)
                binding.itemImg.setImageDrawable(null)
            }
        }
            val animatorSet = AnimatorSet()
            val rotateAnimator = ObjectAnimator.ofFloat(view, "rotationY", 0f, 180f)
            rotateAnimator.duration = 400

            val scaleXAnimator = ValueAnimator.ofFloat(1f, -1f)
            scaleXAnimator.addUpdateListener { animator ->
                val scale = animator.animatedValue as Float
                view.scaleX = scale
            }
            scaleXAnimator.duration = 400

            animatorSet.playTogether(rotateAnimator, scaleXAnimator)
            animatorSet.start()
        }

}