/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olivesynctt;

/**
 *
 * @author drathod
 */
public class Room 
{
    String room_name;
    int capacity_of_room;
    String location;
    int[][] availability;
    
    public Room()
    {
        availability = new int[OliveSyncTT.days_in_week][OliveSyncTT.hour_in_class];
    }
    
    public void set_roomname(String a)
    {
        room_name = a;
    }
    
    public String get_roomname()
    {
        return room_name;
    }
    
    public void set_capacity(int a)
    {
        capacity_of_room = a;
    }
    
    public int get_capacity()
    {
        return capacity_of_room;
    }
    
    public void set_location(String h)
    {
        location = h;
    }
    
    public String get_location()
    {
        return location;
    }
    
    public void set_availability(int[][] a)
    {
        availability = a;
    }
    
    public int[][] get_availability()
    {
        return availability;
    }
}
