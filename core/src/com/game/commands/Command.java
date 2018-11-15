package com.game.commands;

import com.badlogic.gdx.Gdx;

public abstract class Command {
    String name;
    int keyboard;

    Command(){
    }
    public static Class cls;
    public abstract void execute();


    boolean pressed(){
        boolean special=false;
        if(keyboard==59||keyboard==60){//check shift
            if(Gdx.input.isKeyPressed(59) || Gdx.input.isKeyPressed(60)){
                special=true;
            }
        }
        return (Gdx.input.isKeyPressed(keyboard) || special);
    }




}