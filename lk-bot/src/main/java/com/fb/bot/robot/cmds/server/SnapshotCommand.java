package com.fb.bot.robot.cmds.server;

import com.fb.bot.robot.Bot;
import com.fb.bot.robot.cmds.Command;
import com.fb.bot.robot.cmds.MessageCommand;
import com.fb.messages.server.general.Snapshot;

@MessageCommand(type=Snapshot.class)
public class SnapshotCommand extends Command<Snapshot>{

    public SnapshotCommand(Bot bot, Snapshot msg) {
	super(bot, msg);
	// TODO Auto-generated constructor stub
    }

    @Override
    public Object execute() throws Exception {
	getBot().addRooms(getMessage().getRooms());
	return null;
    }

}
