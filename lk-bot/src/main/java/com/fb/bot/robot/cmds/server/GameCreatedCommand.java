package com.fb.bot.robot.cmds.server;

import java.util.ArrayList;

import com.fb.bot.robot.Bot;
import com.fb.bot.robot.cmds.Command;
import com.fb.bot.robot.cmds.MessageCommand;
import com.fb.messages.server.general.Snapshot.Room;
import com.fb.messages.server.room.GameCreated;

@MessageCommand(type = GameCreated.class)
public class GameCreatedCommand extends Command<GameCreated> {

    public GameCreatedCommand(Bot bot, GameCreated msg) {
	super(bot, msg);
    }

    @Override
    public Object execute() throws Exception {
	ArrayList<String> players = new ArrayList<String>();
	players.add(getMessage().getCreatorUserId());
	Room room = new Room(getMessage().getGameName(), getMessage().getGameId(), players, getMessage()
		.getCreatorUserId());
	getBot().addRoom(room);
	return room;
    }
}
