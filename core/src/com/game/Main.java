package com.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.game.commands.Command;
import com.game.commands.LeftComm;
import com.game.commands.RightComm;
import com.game.commands.ShootComm;
import com.game.states.GameStateManager;
import com.game.states.MenuState;


import java.util.ArrayList;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	GameStateManager gsm;
	public final static int HIGHT = 900;
	public final static int WIDTH = 600;
	public final static float ft=1f/60f;
	public static ArrayList<Command> queue;
	public static BitmapFont font;



	@Override
	public void create () {
		Gdx.graphics.setTitle("Breaker");
		Gdx.graphics.setWindowedMode(WIDTH,HIGHT);
		font =new BitmapFont();
		queue = new ArrayList<>(3);
		batch = new SpriteBatch();
		gsm= new GameStateManager();
		//gsm.push(new GameState(gsm));
		gsm.push(new MenuState(gsm));

		queue.add(new LeftComm());
		queue.add(new RightComm());
		queue.add(new ShootComm());

	}

	@Override
	public void render () {
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);

	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
