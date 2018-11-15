package com.game.breaker;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.game.shapes.Ngon;
import com.game.shapes.ShapeRendererExt;
import com.game.states.GameState;

import static com.game.states.GameState.player;

public class PowerUp {
    public Ngon shape = new Ngon();
    int type =0;
    Vector2 vel = new Vector2(0,-3);
    boolean active = false;
    public PowerUp(Ngon shape, int type){
        this.shape=shape;
        this.type=type;
    }
    public PowerUp(Vector2 p, int type){
        this(new Ngon(p,20f,5,0),type);
    }
    public void update(float dt){
            shape.move(vel);
    }
    public void render(ShapeRendererExt sr){
        sr.ngon(shape);
    }

    public Ngon getShape(){return shape;}

    public void setActive() {
        active=true;
    }

    public void collide() {
        switch (type){
            case 0:{
                player.doubleWidth();
                break;
            }
            case 1:{
                GameState.duplicateBalls();
                break;
            }
        }
    }
}
