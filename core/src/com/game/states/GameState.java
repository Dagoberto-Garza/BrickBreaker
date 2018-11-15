package com.game.states;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.Main;
import com.game.breaker.*;
import com.game.commands.Command;
import com.game.shapes.Circle;
import com.game.shapes.Line;
import com.game.shapes.ShapeRendererExt;
import com.game.shapes.Triangle;
import com.game.tools.Delta;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;
import static com.game.Main.*;
import static com.game.commands.Command.cls;
public class GameState extends State {
    public static Random rn = new Random();
    public static Ship player;
    public static ShapeRendererExt shapeRenderer;
    public static ArrayList<Brick> bricks = new ArrayList<>();
    public static ArrayList<PowerUp> activePowers = new ArrayList<>();
    public static ArrayList<Ball> extraBalls=new ArrayList<>();
    public static Ball ball;
    Delta dtNextLvl = new Delta(50*ft);

    public boolean start = true;
    String collOutputDebug = "";
    int level =0;
    String rectangle = "";
    public static float dtScl = 0;
    Sound gameOver = Gdx.audio.newSound(Gdx.files.internal("sfx/gameover.mp3"));
    public static ArrayList<int [][]> levelList = new ArrayList<>(10);
   public static Sound music = Gdx.audio.newSound(Gdx.files.internal("sfx/ChipOnYourShoulder.mp3"));

    public GameState(GameStateManager gsm) {
        super(gsm);

       music.play(.7f);

        shapeRenderer = new ShapeRendererExt();
        shapeRenderer.setAutoShapeType(true);
        InitFile();
        player = new Ship();
        Brick.initColor();
        ball = new Ball(new Vector2(WIDTH / 2, 200));
        ball.setColor(Color.WHITE);
        cls = GameState.class;
        Brick.setBricks(levelList.get(0));
        int [][] temp = levelList.get(0);

        if (start) {
            ball.start();
            start = false;
        }
    }

    @Override
    protected void handleInput() {

        for (Command c : Main.queue) {
            c.execute();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F1)) {
            ball.reset();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.F2)){

            Brick.setBricks(levelList.get(level++));

            }

        }


    @Override
    public void update(float dt) {
        dtScl = dt * 6;
        handleInput();
        dtNextLvl.update(dt);
        collisionHandeler(ball);
        try {
            for (Ball b : extraBalls) {
                collisionHandeler(b);
            }
        }catch (ConcurrentModificationException e){

        }
        player.update(dt);
        for (Brick b : bricks) {
            b.update(dt);
        }
        for(PowerUp p : activePowers){
            p.update(dt);
        }
        for(Ball p : extraBalls){
            p.update(dt);
        }
        ball.update(dt);
        nextLvl();
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin();
        player.display(shapeRenderer);
        bricks.forEach(x -> x.display(shapeRenderer));
        ball.render(shapeRenderer);
        shapeRenderer.setColor(Color.WHITE);
        for(PowerUp p : activePowers){
            p.render(shapeRenderer);
        }
        for(Ball p : extraBalls){
            p.render(shapeRenderer);
        }
        shapeRenderer.end();
        sb.begin();
        font.setColor(1, 1, 1, 1);
        font.draw(sb, "Score: " + player.getScore()+"   Lives: " + (player.getLives()-1)+"  Level:"+(level+1), 30, HIGHT - 25);
        //font.draw(sb, collOutputDebug, 40, 150);
        sb.end();
    }

    @Override
    public void dispose() {

    }


    public void collisionHandeler(Ball ball) {

        int ran = rn.nextInt(10) - 5;
        Vector2 vel = new Vector2(ball.getVel());
        Collision coll = new Collision();
        for (int x = bricks.size() - 1; x >= 0; x--) {
            Brick b = bricks.get(x);
            if (ball.getShape().overlaps(b.getShape())) {
                if(b.hasPowerup()){
                    b.getPower().setActive();
                    activePowers.add(b.getPower());
                }
                boolean check = false;
                for (Triangle t : Triangle.triangulate(b.getShape())) {
                    if (ball.getShape().overlaps(t) && !check) {
                        ArrayList<Line> lines = Line.asLines(t);
                        for (Line l : lines) {
                            if (Math.round(l.returnDx()) == 0 && Math.floor(l.returnDy()) != 0) {
                                ball.setVel(new Vector2(-vel.x, vel.y));
                                check = true;
                            } else if (Math.round(l.returnDx()) != 0 && Math.round(l.returnDy()) == 0) {
                                ball.setVel(new Vector2((vel.x), -vel.y));
                                check = true;
                            }
                        }
                    }
                }

                b.hit();


            }

        }
        //right border
        if (ball.getShape().center.x + ball.getShape().radius >= WIDTH) {
            ball.setVel(new Vector2(-vel.x, vel.y));

        }
        //player coll
        if(true) {
            Rectangle r = player.getShape();
            float y1 = r.y + r.height + 1;
            Line l = new Line(new Vector2(r.width + r.x, y1), new Vector2(r.x, y1));

            Circle c = ball.getShape();
            boolean a = c.center.y - c.radius <= y1;
            boolean b = c.center.x + c.radius <= l.a.x;
            boolean x = c.center.x - c.radius >= l.b.x;
            boolean q = c.center.y > l.a.y;
            boolean i = c.center.dst(l.a) < c.radius;
            boolean j = c.center.dst(l.b) < c.radius;
            collOutputDebug = a + " " + b + " " + x + " " + q + " " + i + " " + j;
            if ((a && b && x && q) || i || j) {
                Vector2 v = player.getVel();
                ball.setVel(new Vector2((vel.x) + (float) (10 * rn.nextGaussian()), (float) (-vel.y + (Math.abs(4 * rn.nextGaussian())))));
                if (v.x > 0 && ball.getVel().x > 0) {
                    ball.getVel().x *= -1.3;
                } else if (v.x < 0 && ball.getVel().x < 0) {
                    ball.getVel().x *= -1.3;
                }
                while ((a && b && x && q) || i || j) {
                    c.center.y += 1f;
                    a = c.center.y - c.radius <= y1;
                    b = c.center.x + c.radius <= l.a.x;
                    x = c.center.x - c.radius >= l.b.x;
                    q = c.center.y < l.a.y;
                    i = c.center.dst(l.a) < c.radius;
                    j = c.center.dst(l.b) < c.radius;
                }

            }
            if(l.b.x>=WIDTH){
                player.setPos(new Vector2(WIDTH-(l.length()),50));
            }
            if((l.a.x)<=0){
                player.setPos(new Vector2(0,50));
            }

        }
        //loss
        if (ball.getShape().center.y < 0) {
            if(ball.equals(GameState.ball)) {
                player.setDeath();
                ball.reset();
                if (player.isGameOver()) {
                    MenuState.finalScore = player.getScore();
                    gsm.pop();
                    music.stop();
                    gameOver.play(1f);
                }
            }else extraBalls.remove(ball);
        }
        //top border
        if (ball.getShape().center.y + ball.getShape().radius >= HIGHT) {
            ball.setVel(new Vector2(vel.x, -vel.y));

        }
        //left border
        if (ball.getShape().center.x - ball.getShape().radius <= 0) {
            ball.setVel(new Vector2(-vel.x, vel.y));
        }

        //check powerup coll
        for(int i=activePowers.size()-1;i>=0;i--){
            PowerUp p = activePowers.get(i);
            boolean a= false;
            boolean b= false;
            for(Triangle t: Triangle.triangulate(p.getShape())){
                if(t.getCenter().y<0){
                    a=true;
                }
                for(Line l:Line.asLines(t)){
                    Rectangle r =player.getShape();
                    Line l2= new Line(new Vector2(r.x,r.y+r.height), new Vector2(r.x+r.width,r.y+r.height));
                    if(Line.intersectsLine(l,l2)){
                        b=true;
                    }
                }

            }
            if(a)
                activePowers.remove(i);
            if(b) {
                p.collide();
                activePowers.remove(i);

            }
        }
    }

    public  void InitFile(){
       ArrayList<String> level=new ArrayList<>();

            try {
                FileReader file = new FileReader("levels.txt");
                BufferedReader bf = new BufferedReader(file);
                String s;

                levelList = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    while ((s = bf.readLine()) != null && !s.equals("")) {
                        level.add(s);
                     //   System.out.println(s);
                    }
                    if(!level.isEmpty()) {
                        int[][] sizes = new int[level.get(0).length()][level.size()];
                        for (int x = 0; x < level.size(); x++) {
                            for (int y = 0; y < level.get(x).length(); y++) {
                                sizes[y][x] = Integer.parseInt(level.get(x).charAt(y) + "");

                            }
                        }
                        levelList.add(sizes);
                        level.clear();
                    }
                }

            }catch(Exception ex) {
                ex.printStackTrace();
            }

        }
    public void nextLvl(){
        if(bricks.size()==0){
            ball.reset();
            level++;
            Brick.setBricks(levelList.get(level));
            extraBalls.clear();
            player.reset();
        }
    }
    public static void duplicateBalls() {
        Ball b=new Ball(ball);
        b.reset();
        Vector2 v =ball.getVel().scl(.8f);
        b.setVel(new Vector2( v.x,(float)Math.abs(v.y)));
        b.setColor(Color.GRAY);
        extraBalls.add(b);

    }
}



