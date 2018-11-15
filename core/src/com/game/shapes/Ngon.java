package com.game.shapes;

import com.badlogic.gdx.math.Vector2;

public class Ngon {
    public Vector2 center= new Vector2();
    float r=0;
    float [] points = new float[16];
    int n=1;
    float angle = 0;

    public Ngon(Vector2 pos, float r, int n){
      this(pos,r,n,0);
    }
    public Ngon(Vector2 pos, float r, int n,float angle){
        this.n=n;
        points = new float[n*2];
        center.set(pos);
        int count =0;
        this.r=r;
        for(int x=0;x<n;x++){
            points[count]= (float) (pos.x + r * Math.cos(Math.toRadians((360/n)*x+angle)));
            points[count+1]=(float) (pos.y + r * Math.sin(Math.toRadians((360/n)*x+angle)));
            count+=2;
        }

    }
     void updatePoints(){
         int count =0;

         for(int x=0;x<n;x++){
            points[count]= (float) (center.x + r * Math.cos(Math.toRadians((360/n)*x+angle)));
            points[count+1]=(float) (center.y + r * Math.sin(Math.toRadians((360/n)*x+angle)));
            count+=2;
        }
    }
    public Ngon(){

    }

    public float[] getVerticies() {
        return points;
    }

    public Vector2 getCenter() {
        return center;
    }

    public int getN() {
        return n;
    }

    public void move(Vector2 vel) {
        center.add(vel);
        updatePoints();
    }
}