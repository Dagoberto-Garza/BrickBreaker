package com.game.breaker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.game.shapes.Circle;
import com.badlogic.gdx.math.Vector2;
import com.game.shapes.ShapeRendererExt;
import com.game.tools.Delta;

import static com.game.Main.HIGHT;
import static com.game.Main.WIDTH;
import static com.game.Main.ft;
import static com.game.states.GameState.dtScl;
import static com.game.states.GameState.rn;


public class Ball {
   private Color c = new Color(rn.nextFloat(), rn.nextFloat(), rn.nextFloat(), 1);
   private boolean death = false;
   private static Delta dt = new Delta(1 * ft);
   private Circle shape;
   private Vector2 vel = new Vector2();
   private Vector2 pos = new Vector2();
   private final Vector2 initVel = new Vector2(50,50);
   private final Vector2 initPos = new Vector2(WIDTH / 2, 200);

    float theta = 0f;

    public Ball(Ball b){
        this(b.pos);
    }
    public Ball(Vector2 pos) {
        shape = new Circle(new Vector2((pos)), 17);
    }

    public Ball(Vector2 pos, float theta) {
        shape = new Circle(new Vector2(pos), 10);
        float m = -10f;
        float vx = m * (float) (Math.cos(Math.toRadians(theta)));
        float vy = m * (float) (Math.sin(Math.toRadians(theta)));
        this.vel.set(vx, vy);
    }

    public void update(float dt) {

        shape.center.add(vel.x*dtScl,vel.y*dtScl);
        pos.set(shape.center);
        fixBounds();
        this.dt.update(dt);

    }

    private void fixBounds() {
        if(shape.center.x+shape.radius>WIDTH){
            shape.center.x=WIDTH-shape.radius-2;
            vel.x*=-1;
        }
        if(shape.center.y+shape.radius>HIGHT){
            shape.center.y=HIGHT-shape.radius-2;
            vel.y*=-1;
        }
        if(shape.center.x-shape.radius<0){
            shape.center.x=shape.radius+2;
            vel.x*=-1;
        }
    }

    public boolean isDeath() {
        return death;
    }

    public void setDeath(boolean death) {
        this.death = death;
    }

    public Circle getShape() {
        return shape;
    }

    public void setShape(Circle shape) {
        this.shape = shape;
    }

    public Vector2 getVel() {
        return vel;
    }

    public void setVel(Vector2 vel) {
        this.vel = vel;
    }

    public void wrap() {
        if (shape.center.x >= WIDTH) {
            shape.center.x = 1;
        }
        if (shape.center.y >= HIGHT) {
            shape.center.y = 1;
        }
        if (shape.center.x <= 0) {
            shape.center.x = WIDTH - 1;
        }
        if (shape.center.y <= 0) {
            shape.center.y = HIGHT - 1;
        }
    }

    public void collided() {
        this.death = true;
    }

    public void render(ShapeRendererExt sr) {
        sr.setColor(c);
        sr.set(ShapeRenderer.ShapeType.Filled);
        sr.circle(shape);
    }

    public void start() {

        this.vel.set(initVel);

        //shape.center.add(vel);

    }

    public void setTheta(float theta) {
        this.theta = theta;

    }

    public float getTheta() {
        return theta;
    }

    public void reset() {
        getShape().center.set(initPos);
        this.vel.set(initVel);
    }

    public void addVel(float v, float v1) {
        vel.add(new Vector2(v,v1));
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos.set(pos);
    }

    public void setColor(Color color) {
        this.c = color;
    }
}
