package com.under.game.domain.game

data class GameItem(
    val value: Int,
    var isOpened: Boolean = false,
    var openAnimation: Boolean = false,
    var closeAnimation: Boolean = false
)