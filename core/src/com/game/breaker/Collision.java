package com.game.breaker;

import com.badlogic.gdx.math.Vector2;
import com.game.shapes.Ngon;
import com.game.shapes.Triangle;

public class Collision {

    public boolean bulletRockColl(Ball bullet, Ngon ngon){
       for(Triangle t: Triangle.triangulate(ngon)){
          if (bullet.getShape().overlaps(t))
              return true;
       }
       return false;
    }
   /* public boolean shipRockColl(Ship s, Ngon ngon){
        for(Triangle t: Triangle.triangulate(ngon)){
            if (s.getShape().overlaps(t))
                return true;
        }
        return false;
    }*/
   public boolean shipBulletColl(Ship s, Ball b){
       if(b.getShape().overlaps(s.getShape())){
           return true;
       }
       return  false;
   }
   public boolean bulletBorderColl(Ball b, Vector2 border){
      // if(b.getShape().center.x+b.getShape().radius,)
       return false;
   }
}
