package com.game.breaker;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.tools.Delta;
import com.game.shapes.ShapeRendererExt;


import java.util.ArrayList;

import static com.game.Main.WIDTH;
import static com.game.Main.HIGHT;
import static com.game.Main.ft;

public class Ship {
    private Rectangle shape= new Rectangle();
    private static Vector2 pos = new Vector2((WIDTH/2)-50, 50);
    private static Vector2 size = new Vector2(100,20);
    private float r = 30f;
    private ArrayList<Ball> bullets = new ArrayList<>(4);
    private Delta dt_shoot;
    private boolean death = false;
    private int score=0;
    private int lives =3;
    public static int finalScore =0;
    private Delta dt_deathcount;
    private Sound bleep;
    private int tempScore =0;
    Vector2 vel = new Vector2();
    Vector2 prePos= new Vector2();


    public Ship() {
        shape.set(pos.x, pos.y, size.x, size.y);
        dt_shoot = new Delta(12 * ft);
        lives = 3;
        death = false;
        dt_deathcount = new Delta(3 * ft);

    }
    public void display(ShapeRendererExt sr) {

        sr.set(ShapeRendererExt.ShapeType.Filled);
        sr.setColor(new Color(1, 0, 0, 1));


        sr.rect(shape);



    }

    public void updateShape() {
        shape.setPosition(pos);

    }

    public Rectangle getShape() {
        return shape;
    }

    public void setShape(Rectangle shape) {
        this.shape = shape;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public float getR() {
        return r;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setR(float r) {
        this.r = r;
    }

    public static void move(Vector2 location) {

        pos.add(location);
    }




    public void wrap() {
        if (pos.x >= WIDTH) {
            pos.x = 1;
        }
        if (pos.y >= HIGHT) {
            pos.y = 1;
        }
        if (pos.x <= 0) {
            pos.x = WIDTH - 1;
        }
        if (pos.y <= 0) {
            pos.y = HIGHT - 1;
        }
    }

    public void update(float dt) {
         vel = new Vector2(prePos.x-pos.x,prePos.y-pos.y);
        dt_shoot.update(dt);
        dt_deathcount.update(dt);
        
        updateShape();
        finalScore = score;
        prePos.set(pos);
        
       

    }

    public static void shoot(Ship s) {
        s.shoot();
    }

    public void shoot() {
        if (dt_shoot.isDone()) {
            bleep.play(.8f);
            if (bullets.size() < 4) {
               // bullets.add(new Ball(pos, angle));
            } else {
                for (int i = bullets.size() - 1; i >= 0; i--) {
                    if (bullets.get(i).isDeath()) {
                        bullets.get(i).setDeath(true);
                        bullets.remove(i);
                    }
                }
               // if (bullets.size() < 4)
                  //  bullets.add(new Ball(pos, angle));
            }

            dt_shoot.reset();
        }
    }


    public ArrayList<Ball> getBullets() {
        return bullets;
    }

    public void setDeath() {
            lives-=1;

    }

    public int getScore() {
        return score;
    }
    public void addScore(int score){
        this.score+=score;
    }


    public boolean getDeath() {
        return death;
    }

    public void extraLife(int goal){


    }
    public void setTempScore(int score){
        tempScore+=score;
    }

    public boolean isGameOver() {
        return lives==0;
    }


    public Vector2 getVel() {
        return vel;
    }

    public int getLives() {
        return lives;
    }

    public void doubleWidth() {
        if(shape.width<=size.x)
        shape.width*=2;
    }

    public void reset() {
        shape.set(pos.x,pos.y,size.x,size.y);

    }


}
