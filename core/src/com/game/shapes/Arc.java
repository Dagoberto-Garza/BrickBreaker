package com.game.shapes;

import com.badlogic.gdx.math.Vector2;


public class Arc {
    Vector2 pos;
    float radius;
    float start;
    float angle;
    public Arc(){}
    public Arc(Vector2 pos,float radius, float start, float angle){
        this.pos=pos;
        this.radius=radius;
        this.start=start;
        this.angle=angle;
    }

}
