/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olivesynctt;

import java.util.Date;

/**
 *
 * @author drathod
 */
public class lecturer
{
        String name_of_lecturer; 
        int lecturer_code_type;
        String home_address;
        String Office_address;
        public static class slots 
        {
            Date start_time;
            Date end_time;
            slots next;
        }
        slots[] head;
        int[][] availability;
        
        public lecturer()
        {
            availability = new int[OliveSyncTT.days_in_week][OliveSyncTT.hour_in_class];
            //head = null;
            //head = new slots();
        }
        
        public void set_name_of_lecturer(String h)
        {
            name_of_lecturer = h;
        }
        
        public String get_name_of_lecturer()
        {
            return name_of_lecturer;
        }
        
        public void set_lecture_code(int h)
        {
            lecturer_code_type = h;
        }
        
        public int get_lecturer_code()
        {
            return lecturer_code_type;
        }
        
        public void set_home_location(String h)
        {
            home_address = h;
        }
        
        public String get_home_location()
        {
            return home_address;
        }
        
        public void set_office_location(String h)
        {
            Office_address = h;
        }
        
        public String get_office_location()
        {
            return Office_address;
        }
        
       public void set_availability(int[][] a)
       {
           availability = a;
       }
       
       public int[][] get_availabilty()
       {
           return availability;
       }
}
