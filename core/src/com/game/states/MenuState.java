package com.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.tools.Delta;
import static com.game.Main.ft;
import static com.game.Main.font;
import static com.game.Main.HIGHT;
import static com.game.Main.WIDTH;
public class MenuState extends State {
    Delta dtStart = new Delta(10*ft);
   public static int finalScore =0;
    public MenuState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    protected void handleInput() {

        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)&&dtStart.isDone()){
            gsm.push(new GameState(gsm));
            dtStart.reset();
        }

    }

    @Override
    public void update(float dt) {
        dtStart.update(dt);
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        font.getData().setScale(5,15);
        GlyphLayout layout = new GlyphLayout(font,"BRICK BREAKER");
        font.draw(sb,layout, (WIDTH/2)-(layout.width/2),(HIGHT-200));
        font.getData().setScale(2);
        layout.setText(font,"PRESS ANY KEY");
        font.draw(sb,layout,(WIDTH/2)-(layout.width/2),(HIGHT/2)-200);
        if(finalScore!=0){
            layout.setText(font,"FINAL SCORE:"+finalScore);
            font.draw(sb,layout,(WIDTH/2)-(layout.width/2),(HIGHT/2));
        }
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
