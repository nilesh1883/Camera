package com.mycash.game.snakeladder.utils

import com.mycash.game.snakeladder.utils.BoardType.EASY_LEVEL_MAX_COUNT
import com.mycash.game.snakeladder.utils.BoardType.HARD_LEVEL_MAX_COUNT
import com.mycash.game.snakeladder.utils.BoardType.MEDIUM_LEVEL_MAX_COUNT
import com.mycash.game.snakeladder.utils.DifficultyLevel.GAME_LEVEL_EASY
import com.mycash.game.snakeladder.utils.DifficultyLevel.GAME_LEVEL_HARD
import com.mycash.game.snakeladder.utils.DifficultyLevel.GAME_LEVEL_MEDIUM

object GameUtils {

    fun getGameBoard(level: Int) {

        when (level) {
            GAME_LEVEL_EASY -> {

            }
        }
    }

    fun getBoardMaxSize(level: Int): Int {
        when (level) {
            GAME_LEVEL_EASY -> {
                return EASY_LEVEL_MAX_COUNT
            }
            GAME_LEVEL_MEDIUM -> {
                return MEDIUM_LEVEL_MAX_COUNT
            }
            GAME_LEVEL_HARD -> {
                return HARD_LEVEL_MAX_COUNT
            }

            else -> {
                return MEDIUM_LEVEL_MAX_COUNT
            }
        }
    }

}