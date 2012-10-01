package com.fb.rooms.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.fb.util.IDGenerator;

public class Room {

    private String id;
    private String creatorId;
    private List<String> users;
    private String name;
    private List<String> map;

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
	return ((o instanceof Room) && ((Room) o).getId().equals(id) && ((Room) o).getCreatorId().equals(creatorId));
    }

    @Override
    public int hashCode() {
	return id.hashCode();
    }

    /**
     * the map size will be noplayers*noplayers
     */
    public void initializeMap() {
	map = new LinkedList<String>();
	for (int i = 0; i < users.size()*users.size(); i++) {
	    map.add(String.valueOf(i));
	}
    }

    public List<String> getMap() {
	return map;
    }
}
