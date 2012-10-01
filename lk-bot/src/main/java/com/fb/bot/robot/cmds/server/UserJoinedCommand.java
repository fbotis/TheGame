package com.fb.bot.robot.cmds.server;

import com.fb.bot.robot.Bot;
import com.fb.bot.robot.cmds.Command;
import com.fb.bot.robot.cmds.MessageCommand;
import com.fb.messages.server.room.UserJoinedGame;

@MessageCommand(type = UserJoinedGame.class)
public class UserJoinedCommand extends Command<UserJoinedGame> {

    public UserJoinedCommand(Bot bot, UserJoinedGame msg) {
	super(bot, msg);
	// TODO Auto-generated constructor stub
    }

    @Override
    public Object execute() throws Exception {
	getBot().userJoinedRoom(getMessage().getJoinedGameId(), getMessage().getJoinedUserId());
	return null;
    }

}
