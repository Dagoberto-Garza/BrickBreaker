package com.game.commands;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.game.breaker.Ship;
import com.game.states.GameState;

import static com.game.states.GameState.dtScl;

public class LeftComm extends Command {
    public LeftComm(){

    }

    @Override
    public void execute() {
        if (cls.equals(GameState.class)) {
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
               Ship.move(new Vector2(-150*(dtScl),0));

            }
        }

    }
}
