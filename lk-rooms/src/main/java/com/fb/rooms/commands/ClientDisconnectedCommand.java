package com.fb.rooms.commands;

import com.fb.messages.client.ClientDisconnected;
import com.fb.rooms.RoomsManager;
import com.fb.transport.IServerMessageSender;

@MessageCommand(type = ClientDisconnected.class)
public class ClientDisconnectedCommand extends Command<ClientDisconnected> {

    public ClientDisconnectedCommand(RoomsManager roomsManager, IServerMessageSender serverSender,
	    ClientDisconnected clientDisconnected) {
	super(roomsManager, serverSender, clientDisconnected);
    }

    @Override
    public void doWork() throws Exception {
	getRoomsManager().userDisconnected(getMessage());

    }

}
