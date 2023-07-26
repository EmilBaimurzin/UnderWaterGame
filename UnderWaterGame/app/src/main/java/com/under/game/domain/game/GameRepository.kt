package com.under.game.domain.game

class GameRepository {
    fun generateList(amount: Int): List<GameItem> {
        val listToReturn = mutableListOf<GameItem>()
        repeat(amount) {
            val value = when (it + 1) {
                in 1..2 -> 1
                in 3..4 -> 2
                in 5..6 -> 3
                in 7..8 -> 4
                in 9..10 -> 5
                else -> 6
            }
            listToReturn.add(GameItem(value))
        }
        listToReturn.shuffle()
        return listToReturn
    }
}