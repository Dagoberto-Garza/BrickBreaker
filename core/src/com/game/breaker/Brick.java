package com.game.breaker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.shapes.Ngon;
import com.game.shapes.ShapeRendererExt;
import com.game.states.GameState;
import com.game.tools.Delta;
import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;

import static com.game.Main.HIGHT;
import static com.game.Main.WIDTH;
import static com.game.Main.ft;
import static com.game.states.GameState.player;
import static com.game.states.GameState.rn;


public class Brick {

    Color c = new Color();
    private Rectangle shape;
    private int n = 8;
    private int r = 20;
    private Vector2 pos = new Vector2();

    private float angle = 45;
    private boolean death = false;
    private int size = 1;
    private static ArrayList<Color> colors = new ArrayList<>(4);
    private Delta dt_flash = new Delta(40 * ft);
    private PowerUp power;
    Sound brokenSound = Gdx.audio.newSound(Gdx.files.internal("sfx/boom.mp3"));

    public Brick(Vector2 pos, int r, int n) {
        this(pos, r, n, 3, 45);

    }

    public Brick(Vector2 pos, int r, int n, int size, float angle) {
        this.pos.set(pos);
        this.r = r;
        this.n = n;
        shape = new Rectangle(pos.x, pos.y, 80, 30);
        this.size = size;
        c.set((rn.nextInt(190) + 65) / 255f, (rn.nextInt(190) + 65) / 255f, (rn.nextInt(190) + 65) / 255f, 1);


    }

    public Brick() {
        this(new Vector2((int) (Math.random() * WIDTH), (int) (Math.random() * HIGHT)), 100, 5);
    }


    public Vector2 ranVel() {
        Vector2 pos = new Vector2();
        float m = 1.7f;
        pos.x = (float) (m * Math.cos(Math.toRadians(rn.nextInt(360))));
        pos.y = (float) (m * Math.sin(Math.toRadians(rn.nextInt(360))));

        return pos;
    }


    public void display(ShapeRendererExt sr) {

      sr.setColor(colors.get(size-1));
        sr.set(ShapeRenderer.ShapeType.Filled);
        sr.rect(shape);


    }

    public void update(float dt) {
        dt_flash.update(dt);
        updateShape();


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

    public Rectangle getShape() {
        return shape;
    }

    public void setShape(Rectangle shape) {
        this.shape = shape;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }


    public float setRanAngle(int n) {
        return (float) Math.random() * (360);
    }


    public void updateShape() {
    }

    public static ArrayList<Brick> generate(int n) {
        ArrayList<Brick> rocks = new ArrayList<>();

        for (int x = 0; x < n; x++) {
            Vector2 pos = new Vector2();
            int r = 100;
            int num = rn.nextInt(8) + 3;
            rocks.add(new Brick(pos, r, num));
        }
        return rocks;
    }

    public void setSize(int size) {
        this.size = size;
        if(this.size==5){
            power=new PowerUp(new Vector2(shape.x,shape.y),0);
        }
        if(this.size==6){
            power=new PowerUp(new Vector2(shape.x,shape.y),1);
        }
    }


    public static void initColor() {
        colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.WHITE);
        colors.add(Color.LIGHT_GRAY);

    }

    public void wasShot() {
        this.death = true;

    }

    public boolean isDead() {
        return death;
    }

    public int getSize() {
        return size;
    }
    public static void setBricks(int [][] level){
            int n =level.length;
            int m =level[0].length;
            int total = n * m;
            GameState.bricks = new ArrayList<>(total);
            float w = 82;
            float scalex = ((WIDTH / 2) - ((w * (n)) / 2));

            for (int x = 0; x < n; x++) {
                for (int y = 0; y < m; y++) {
                    if (level[x][y] != 0) {
                        Brick b = new Brick(new Vector2(scalex + (x * w), HIGHT - (y * 32) - 100), 40, 4);
                        b.setSize(level[x][y]);
                        GameState.bricks.add(b);
                    }
                }
            }
        }

    public void hit() {
        int points =0;
        switch (size){
            case 1:{
                points=150;
                break;
            }
            case 2:{
                points=200;
                break;
            }
            case 3:{
                points=250;
                break;
            }
            case 4:{
                points=300;
                break;
            }
        }
        GameState.player.addScore(points);
        size--;
        if(size<1){
            brokenSound.play(1f);
            GameState.bricks.remove(this);
        }
    }
    public void addPowerup(PowerUp power){
        this.power=power;
    }

    public boolean hasPowerup() {
        return size==5 || size== 6;
    }

    public PowerUp getPower() {
        power.shape.center.set(shape.x+(shape.width/2), shape.y+(shape.height/2));
        return power;
    }
}

