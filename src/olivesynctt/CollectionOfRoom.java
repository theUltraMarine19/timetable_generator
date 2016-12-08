/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olivesynctt;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author drathod
 */
public class CollectionOfRoom 
{
    List<Room> list_of_rooms;
    int number_of_rooms;
    
    public CollectionOfRoom() 
    {
        list_of_rooms = new ArrayList<>(OliveSyncTT.max_room_num);
        number_of_rooms = 0;
    }
    
    public void addRooms(Room z)
    {
        list_of_rooms.add(z);
        number_of_rooms++;
    }
    
    public Room get_room(int a)
    {
        return list_of_rooms.get(a);
    }
    
    public void set_num_of_room(int a)
    {
        number_of_rooms = a;
    }
    
    public int get_num_of_room()
    {
        return number_of_rooms;
    }
    
}
