package com.game.tools;

public class Delta {
    float dtPassed = 0;
    float limit=0;
    public Delta(float limit) {
        this.limit=limit;
    }
    public void update(float dt) {
        if(dtPassed<=limit) {
            dtPassed += dt;
        }
    }
    public boolean isDone(){
        return dtPassed>limit;
    }
    public void reset(){
        dtPassed=0;
    }
}
