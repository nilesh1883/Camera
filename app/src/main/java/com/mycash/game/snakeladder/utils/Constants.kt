package com.mycash.game.snakeladder.utils

/**
 * Difficulty level for game
 */
object DifficultyLevel{

    const val GAME_LEVEL_EASY = 1
    const val GAME_LEVEL_MEDIUM = 2
    const val GAME_LEVEL_HARD = 3
}

/**
 * Game board max count based on difficulty level
 */
object BoardType{
    const val EASY_LEVEL_MAX_COUNT = 6 * 6 //36
    const val MEDIUM_LEVEL_MAX_COUNT = 8 * 8 //64
    const val HARD_LEVEL_MAX_COUNT = 10 * 10 //100
}
