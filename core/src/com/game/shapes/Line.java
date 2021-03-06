package com.game.shapes;



import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;


public class Line {
    public Vector2 a=new Vector2();
    public Vector2 b=new Vector2();
    public float dx;
    public float dy;
    public Line(){

    }
    public Line(Vector2 a,Vector2 b){
        this.a=a;
        this.b=b;
    }

    public static boolean intersectsLine(Line a, Line b){
            float denominator = ((a.b.x - a.a.x) * (b.b.y - b.a.y)) - ((a.b.y - a.a.y) * (b.b.x - b.a.x));
            float numerator1 = ((a.a.y - b.a.y) * (b.b.x - b.a.x)) - ((a.a.x - b.a.x) * (b.b.y - b.a.y));
            float numerator2 = ((a.a.y - b.a.y) * (a.b.x - a.a.x)) - ((a.a.x - b.a.x) * (a.b.y - a.a.y));

            // Detect coincident lines (has a problem, read below)
            if (denominator == 0) return numerator1 == 0 && numerator2 == 0;

            float r = numerator1 / denominator;
            float s = numerator2 / denominator;

            return (r >= 0 && r <= 1) && (s >= 0 && s <= 1);
    }
    public float returnDx(){
     dx = b.x-a.x;
     return dx;
    }
    public float returnDy(){
        dy = b.y-a.y;
        return dy;
    }
    public static ArrayList<Line> asLines(Triangle t){
        float[] points = t.getPoints();
        ArrayList<Line> triLines = new ArrayList<>();
        Vector2 a=new Vector2(points[0],points[1]),
                b=new Vector2(points[2],points[3]),
                c=new Vector2(points[4],points[5]);
       /* a.set(a.x,fixHeight(a));
        b.set(b.x,fixHeight(b));
        c.set(c.x,fixHeight(c));
*/
        triLines.add(new Line(a,b));
        triLines.add(new Line(b,c));
        triLines.add(new Line(c,a));
        return triLines;
    }
    public static ArrayList<Line> asLines(Rectangle r){
        ArrayList<Line> rect = new ArrayList<>();
        Vector2 a=new Vector2(r.x,r.y),
                b=new Vector2(r.x,r.y+r.height),
                c=new Vector2(r.x+r.width,r.y+r.height),
                d=new Vector2(r.x+r.width,r.y);
  /*      a.set(a.x,fixHeight(a));
        b.set(b.x,fixHeight(b));
        c.set(c.x,fixHeight(c));
        d.set(d.x,fixHeight(d));
*/
        rect.add(new Line(a,b));
        rect.add(new Line(b,c));
        rect.add(new Line(c,d));
        rect.add(new Line(d,a));
        return rect;
    }
    public float length(){
        return a.dst(b);
    }
}
