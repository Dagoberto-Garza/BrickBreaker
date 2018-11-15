package com.game.shapes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;


public class ShapeRendererExt extends ShapeRenderer {

    public void rect(Rectangle r) {
        rect(r.x,r.y,r.width,r.height);
    }
    public void triangle(Triangle t){
        float[] f=t.getPoints();
        triangle(f[0],f[1],f[2],f[3],f[4],f[5]);
    }
    public void circle(Circle c){
        circle(c.center.x,c.center.y,c.radius);
    }
    public void ngon(Ngon o){
        polygon(o.getVerticies());
    }

    public void ngonFill(Ngon o){
        for(Triangle t: Triangle.triangulate(o)){
            triangle(t.a().x,t.a().y,t.b().x,t.b().y,t.c().x,t.c().y);

        }
    }
    public void line(Line l){
        line(l.a,l.b);
    }
    public void arc(Arc a){
        arc(a.pos.x,a.pos.y,a.radius,a.start,a.angle);
    }
}