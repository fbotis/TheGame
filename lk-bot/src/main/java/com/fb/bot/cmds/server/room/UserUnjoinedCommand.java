package com.fb.bot.cmds.server.room;

import com.fb.bot.Bot;
import com.fb.bot.cmds.Command;
import com.fb.bot.cmds.MessageCommand;
import com.fb.messages.server.room.UserUnjoinedGame;

@MessageCommand(type = UserUnjoinedGame.class)
public class UserUnjoinedCommand extends Command<UserUnjoinedGame> {

    public UserUnjoinedCommand(Bot bot, UserUnjoinedGame msg) {
	super(bot, msg);
    }

    @Override
    public Object execute() throws Exception {
	getBot().userUnjoinedRoom(getMessage().getUnjoinedGameId(), getMessage().getUnjoinedUserId());
	return null;
    }

}
