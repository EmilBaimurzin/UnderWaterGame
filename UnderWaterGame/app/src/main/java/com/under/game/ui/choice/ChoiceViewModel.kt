package com.under.game.ui.choice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.under.game.domain.other.Difficulty

class ChoiceViewModel : ViewModel() {
    private val _easyTime = MutableLiveData(30)
    val easyTime: LiveData<Int> = _easyTime

    private val _normalTime = MutableLiveData(30)
    val normalTime: LiveData<Int> = _normalTime

    private val _mediumTime = MutableLiveData(30)
    val mediumTime: LiveData<Int> = _mediumTime

    private val _hardTime = MutableLiveData(30)
    val hardTime: LiveData<Int> = _hardTime

    fun plusTime(difficulty: Difficulty) {
        when (difficulty) {
            Difficulty.EASY -> {
                if (_easyTime.value!! + 15 <= 90) {
                    _easyTime.postValue(_easyTime.value!! + 15)
                }
            }

            Difficulty.NORMAL -> {
                if (_normalTime.value!! + 15 <= 90) {
                    _normalTime.postValue(_normalTime.value!! + 15)
                }
            }

            Difficulty.MEDIUM -> {
                if (_mediumTime.value!! + 15 <= 90) {
                    _mediumTime.postValue(_mediumTime.value!! + 15)
                }
            }

            Difficulty.HARD -> {
                if (_hardTime.value!! + 15 <= 90) {
                    _hardTime.postValue(_hardTime.value!! + 15)
                }
            }
        }
    }

    fun minusTime(difficulty: Difficulty) {
        when (difficulty) {
            Difficulty.EASY -> {
                if (_easyTime.value!! - 15 >= 15) {
                    _easyTime.postValue(_easyTime.value!! - 15)
                }
            }

            Difficulty.NORMAL -> {
                if (_normalTime.value!! - 15 >= 15) {
                    _normalTime.postValue(_normalTime.value!! - 15)
                }
            }

            Difficulty.MEDIUM -> {
                if (_mediumTime.value!! - 15 >= 15) {
                    _mediumTime.postValue(_mediumTime.value!! - 15)
                }
            }

            Difficulty.HARD -> {
                if (_hardTime.value!! - 15 >= 15) {
                    _hardTime.postValue(_hardTime.value!! - 15)
                }
            }
        }
    }
}