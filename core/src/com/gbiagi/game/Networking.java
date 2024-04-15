package com.gbiagi.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Networking extends ApplicationAdapter
{
	Dialog  endDialog;
	Skin    skin;
	Stage   stage;

	@Override
	public void create()
	{
		skin = new Skin(Gdx.files.internal("uiskin.json"));

		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		endDialog = new Dialog("End Game", skin)
		{
			protected void result(Object object)
			{
				System.out.println("Option: " + object);
				Timer.schedule(new Task()
				{

					@Override
					public void run()
					{
						endDialog.show(stage);
					}
				}, 1);
			};
		};
		endDialog.setSize(500, 200); // Set the width and height of the Dialog
		TextButton button1 = new TextButton("Option 1", skin);
		button1.setSize(400, 150); // Set the width and height of the button
		endDialog.button(button1, 1L);

		TextButton button2 = new TextButton("Option 2", skin);
		button2.setSize(400, 150); // Set the width and height of the button
		endDialog.button(button2, 2L);
		Timer.schedule(new Task()
		{

			@Override
			public void run()
			{
				endDialog.show(stage);
			}
		}, 1);

	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act();
		stage.draw();

	}

	@Override
	public void dispose()
	{
		stage.dispose();
	}
}