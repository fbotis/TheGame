package com.fb.rooms.model;

import java.util.ArrayList;
import java.util.List;

import com.fb.util.IDGenerator;

public class Room {

    private String id;
    private String creatorId;
    private List<String> users;
    private String name;

    public Room(String creatorId, String name) {
	this.id = "Room-" + IDGenerator.INSTANCE.nextID();
	this.creatorId = creatorId;
	this.users = new ArrayList<String>();
	users.add(creatorId);
	this.name = name;
    }

    public List<String> getUsers() {
	return users;
    }

    public String getName() {
	return name;
    }

    public String getId() {
	return id;
    }

    public String getCreatorId() {
	return creatorId;
    }

    public boolean equals(Object o) {
	return ((o instanceof Room) && ((Room) o).getId().equals(id) && ((Room) o)
		.getCreatorId().equals(creatorId));
    }

    @Override
    public int hashCode() {
	return id.hashCode();
    }
}
