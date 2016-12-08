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
public class CollectionOfLecturers {
    
        List<lecturer> list_of_lecturers;
        int number_of_lecturers;
        
        public CollectionOfLecturers()
        {
            list_of_lecturers = new ArrayList<>(OliveSyncTT.max_number_of_lecturers);
            number_of_lecturers = 0;
        }
        
        public void set_num_of_lecturer(int g)
        {
            number_of_lecturers = g;
        }
        
        public int get_num_of_lecturer()
        {
            return number_of_lecturers;
        }
        
        public void add_lecturers(lecturer z)
        {
            list_of_lecturers.add(z);
            number_of_lecturers++;
        }
        
        public lecturer get_lecturer(int i)
        {
            return list_of_lecturers.get(i);
        }
}
