package com.fb.model;

import org.junit.Test;

import com.fb.messages.ClientBaseMessage;
import com.fb.messages.client.room.CreateGame;
import com.google.gson.Gson;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class SmokeTest extends TestCase {
    @Test
    public void testSmoke() throws Exception {
	CreateGame game = new CreateGame("name", "uid");
	System.out.println(game.toString());
	Gson gson = new Gson();
	System.out.println(gson.fromJson(game.toString(), CreateGame.class));
	System.out.println(gson.fromJson(game.toString(),
		ClientBaseMessage.class));

    }
}
