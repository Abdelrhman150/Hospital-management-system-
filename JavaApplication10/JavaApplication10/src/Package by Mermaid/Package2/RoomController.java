package Package2;

import java.io.*;
import java.util.*;
package controller;

import entity.Room;
import dao.RoomDAO;
import dao.RoomIdGenerator;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import Package1.RoomFactory;
import Package1.Room;

/**
 * 
 */
public class RoomController {
    public static RoomFactory factory;
    /**
     * Default constructor
     */
    private RoomDAO roomDAO;

    public RoomController(RoomFactory factory) {
        this.factory = factory;
    }

    public Room reserveRoom(){
        return factory.createRoom();
    }

   

    public RoomController() {
        this.roomDAO = new RoomDAO();
    }

    public void addRoom(String roomType, int capacity, String availabilityStatus, double dailyRate) {
        try {
            String newRoomId = RoomIdGenerator.getInstance().generateRoomId();
            Room newRoom = new Room(newRoomId, roomType, capacity, availabilityStatus, dailyRate);
            roomDAO.addRoom(newRoom);
            System.out.println("Room added successfully: " + newRoomId);
        } catch (SQLException e) {
            System.err.println("Error adding room: " + e.getMessage());
        }
    }

    public List<Room> getAllRooms() {
        try {
            return roomDAO.getAllRooms();
        } catch (SQLException e) {
            System.err.println("Error retrieving rooms: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void updateRoom(String roomId, String roomType, int capacity, String availabilityStatus, double dailyRate) {
        try {
            Room roomToUpdate = new Room(roomId, roomType, capacity, availabilityStatus, dailyRate);
            roomDAO.updateRoom(roomToUpdate);
            System.out.println("Room updated successfully: " + roomId);
        } catch (SQLException e) {
            System.err.println("Error updating room: " + e.getMessage());
        }
    }

    public void deleteRoom(String roomId) {
        try {
            roomDAO.deleteRoom(roomId);
            System.out.println("Room deleted successfully: " + roomId);
        } catch (SQLException e) {
            System.err.println("Error deleting room: " + e.getMessage());
        }
    }
}

