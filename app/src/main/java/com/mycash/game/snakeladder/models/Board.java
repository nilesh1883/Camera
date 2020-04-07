package com.mycash.game.snakeladder.models;


import com.mycash.game.snakeladder.utils.DifficultyLevel;
import com.mycash.game.snakeladder.utils.GameUtils;

public class Board {

    private static Board sInstance = null;

    private Square[] grid;

    public Board(Integer level){
        initBoard(level);
    }

    private void initBoard(Integer level) {
        grid = new Square[GameUtils.INSTANCE.getBoardMaxSize(level)];

    }


}
