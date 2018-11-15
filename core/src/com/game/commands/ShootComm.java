package com.game.commands;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.game.states.GameState;

public class ShootComm extends Command {
    public ShootComm(){

    }
    @Override
    public void execute() {
        if (cls.equals(GameState.class)) {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {

            }
        }
    }
}
