package com.fb.model;

import static junit.framework.Assert.*;

import org.junit.Test;

import com.fb.exceptions.room.RoomNotFoundException;
import com.fb.messages.MsgDeserializer;
import com.fb.messages.client.room.CreateGame;
import com.fb.messages.client.room.JoinGame;
import com.fb.messages.server.ErrorMessage;

public class MsgDeserializerTest {
    @Test
    public void testSmoke() throws Exception {
	MsgDeserializer des = new MsgDeserializer();
	CreateGame game = new CreateGame("uid", "gameName");
	String json = game.toString();
	CreateGame game2 = (CreateGame) des.fromJsonMsg(json);
	assertEquals("uid", game2.getUserId());
	assertEquals("gameName", game2.getGameName());
    }

    @Test
    public void testException() throws Exception {
	RoomNotFoundException roomExce = new RoomNotFoundException(new JoinGame("123", "abc"));
	assertEquals(roomExce.getErrorType(), RoomNotFoundException.class.getName());

	ErrorMessage err = new ErrorMessage(roomExce);
	String ser = err.toString();
	ErrorMessage deserr = (ErrorMessage) new MsgDeserializer().fromJsonMsg(ser);
	assertEquals(err.getClientMsg().getUserId(), deserr.getClientMsg().getUserId());
    }
}
