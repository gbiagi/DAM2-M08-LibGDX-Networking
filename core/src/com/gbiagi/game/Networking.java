package com.gbiagi.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.graphics.OrthographicCamera;

import org.w3c.dom.Text;

import jdk.internal.org.jline.utils.Log;


public class Networking extends ApplicationAdapter
{
	Dialog  endDialog;
	Skin    skin;
	Stage   stage;
	OrthographicCamera camera;


	@Override
	public void create()
	{
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

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
		endDialog.setSize(800, 600); // Set the width and height of the Dialog

		TextButton button1 = new TextButton("HttpRequest", skin);
		button1.addListener(new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			System.out.println("Option 1 Pressed");
			// Create a new HTTP request
			Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
			request.setUrl("https://bible-api.com/?random=verse");
			// Send the HTTP request
			Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
				@Override
				public void handleHttpResponse(Net.HttpResponse httpResponse) {
				String response = httpResponse.getResultAsString();

				// Parse the JSON response
				JsonReader jsonReader = new JsonReader();
				JsonValue jsonValue = jsonReader.parse(response);

				// Extract the "text" field
				String text = jsonValue.getString("text");

				// Print the text to the console
				Log.info("Verse: " + text);
				System.out.println("Text: " + text);
				}

				@Override
				public void failed(Throwable t) {
					System.out.println("Request failed");
				}

				@Override
				public void cancelled() {
					System.out.println("Request cancelled");
				}
			});
		}
		});

		button1.setSize(400, 150); // Set the width and height of the button
		endDialog.button(button1, 1L);

		TextButton button2 = new TextButton("Exit App", skin);
		button2.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.out.println("Option 2 Pressed");
				Gdx.app.exit();
			}
		});
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

		camera.update();
		stage.getBatch().setProjectionMatrix(camera.combined);

		stage.act();
		stage.draw();

	}

	@Override
	public void dispose()
	{
		stage.dispose();
	}
}