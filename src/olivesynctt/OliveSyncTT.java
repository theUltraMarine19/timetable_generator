/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olivesynctt;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author drathod
 */
public class OliveSyncTT {
    static int num_of_trails = 0;
    static int mutation_rate = 16;
    static int max_population_size = 10;
    static int max_num_of_classes = 50;
    static int max_number_of_lecturers = 10;
    static int max_room_num = 3;
    static int days_in_week = 5;
    static int hour_in_class = 10;
    //static float insti_working_hour_start = 8;
    //static float insti_working_hour_end = 8;
    int class_num;
    
    static final long cost_of_related_class_clash = 1;
    static final long cost_of_room_too_small = 100;
    static final long cost_of_lecturer_doubly_booked = 1;
    static final long cost_of_unavailable_lecturer = 20;
    
    public static void outputlines(String x)
    {
        
        try 
        {

             String filename= "tt.txt";
             FileWriter fw = new FileWriter(filename,true); //the true will append the new data
             fw.write(x);//appends the string to the file
             fw.close();
        }
        catch (IOException ex) 
        {
            Logger.getLogger(OliveSyncTT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void outputlines(int x)
    {
        
        try 
        {
             String filename= "tt.txt";
             FileWriter fw = new FileWriter(filename,true); //the true will append the new data
             fw.write(x+"");//appends the string to the file
             fw.close();
        }
        catch (IOException ex) 
        {
            Logger.getLogger(OliveSyncTT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void output(TimeTable timeTable)
    {
        for (int i = 0; i < max_room_num; i++)
        {
            i++;
            outputlines("Room " + i +"\n");
            i--;
            for (int j = 0; j < days_in_week; j++)
            {
                for (int k = 0; k < hour_in_class; k++) 
                {
                    outputlines(timeTable.booking[i][j][k] + "   ");
                }
                outputlines("\n");
            }
        }
    }
    
    public static void initialise_constraints(String j, CollectionOfClass collectionOfClass, CollectionOfLecturers collectionOfLecturers, CollectionOfRoom x)
    {
        int a = 0;
        int day, hour;
        
        try
        {
            
            List<String> lines = Files.readAllLines(Paths.get(j), StandardCharsets.UTF_8);
            
            int i = 0;
            while(!lines.get(i).equals("end"))
            {
                String p = lines.get(i);
                String h[] = p.split(":");
                if ( h[0].equals("num_of_class") )
                {
                    collectionOfClass.set_num_of_class(Integer.parseInt(h[1]));
                    System.out.println(collectionOfClass.get_num_of_class());
                }
                else if ( h[0].equals("num_of_class_association") )
                {
                    collectionOfClass.set_num_of_class_association(Integer.parseInt(h[1]));
                    System.out.println(collectionOfClass.get_num_of_class_association());
                }
                i++;
            }
            i++;   // i = 3
            
            TClass tClass = new TClass();
            tClass.set_name("noclass");
            tClass.set_reference_of_lecturers(0);
            tClass.set_size_of_class(0);
            tClass.set_associated_code_of_class(0);
            tClass.set_room_location("nowhere");
            collectionOfClass.list_of_class.add(tClass);
            
            for (int k = 1; k <= collectionOfClass.get_num_of_class(); k++) 
            {
                TClass tClass1 = new TClass();
                while(!lines.get(i).equals("end"))
                {
                    String p = lines.get(i);
                    String h[] = p.split(":");

                    if(h[0].equals("classname"))
                    {
                        tClass1.set_name(h[1]);
                        System.out.println("classname  " + tClass1.get_name());
                    }
                    else if(h[0].equals("lecturer_reference_number"))
                    {
                        tClass1.set_reference_of_lecturers(Integer.parseInt(h[1]));
                        System.out.println("lecturer_reference_number  " + tClass1.get_reference_of_lecturers());
                    }
                    else if(h[0].equals("classsize"))
                    {
                        tClass1.set_size_of_class(Integer.parseInt(h[1]));
                        System.out.println("classsize  "+tClass1.get_size_of_class());
                    }
                    else if(h[0].equals("classcode"))
                    {
                        tClass1.set_associated_code_of_class(Integer.parseInt(h[1]));
                        System.out.println("classcode  "+tClass1.get_associated_code_of_class());
                    }
                    else if(h[0].equals("classlocation"))
                    {
                        tClass1.set_room_location(h[1]);
                        System.out.println("classlocation  "+tClass1.get_room_location());
                    }
                    i++;
                }
                i++;
                collectionOfClass.list_of_class.add(tClass1);

            }   //  end of adding classes
            
            {
                    String p = lines.get(i);
                    String h[] = p.split(":");
                    if (h[0].equals("numberoflecturer")) 
                    {
                        collectionOfLecturers.set_num_of_lecturer(Integer.parseInt(h[1]));
                        System.out.println("numberoflecturer  " + collectionOfLecturers.get_num_of_lecturer());
                    }
            }   // end of initializing number of lecturers 
            
            i++;
            
            lecturer sir = new lecturer();
            sir.set_name_of_lecturer("nobody");
            sir.set_home_location("nowhere");
            sir.set_office_location("nowhere");
            sir.set_lecture_code(0);
            int[][] temp = new int[days_in_week][hour_in_class];
            for (int k = 0; k < days_in_week; k++)
            {
                for (int l = 0; l < hour_in_class; l++)
                {
                    temp[k][l] = 0;
                }
            }
            sir.set_availability(temp);
            collectionOfLecturers.list_of_lecturers.add(sir);
            for (int k = 0; k < collectionOfLecturers.get_num_of_lecturer(); k++)
            {
                lecturer sir1 = new lecturer();
                int[][] data = new int[5][10];
                 while(!lines.get(i).equals("end"))
                {
                    String p = lines.get(i);
                    String h[] = p.split(":");

                    if(h[0].equals("lecturername"))
                    {
                        sir1.set_name_of_lecturer(h[1]);
                        System.out.println("lecturername  " + sir1.get_name_of_lecturer());
                    }
                    else if(h[0].equals("lecturer_code"))
                    {
                        sir1.set_lecture_code(Integer.parseInt(h[1]));
                        System.out.println("lecturercode  " + sir1.get_lecturer_code());
                    }
                    else if(h[0].equals("locationoflecturerhome"))
                    {
                        sir1.set_home_location(h[1]);
                        System.out.println("locationoflecturerhome  "+sir1.get_home_location());
                    }
                    else if(h[0].equals("locationoflectureroffice"))
                    {
                        sir1.set_office_location(h[1]);
                        System.out.println("locationoflectureroffice  "+sir1.get_office_location());
                    }
                    else if(h[0].equals("availability"))
                    {
                        i++;
                        while(!lines.get(i).equals("end"))
                        {
                            for (int l = 0; l < 5; l++) 
                            {
                                String d = lines.get(i);
                                String v[] = d.split(":");
                                
                                data[l][0] = Integer.parseInt(v[0]);
                                data[l][1] = Integer.parseInt(v[1]);
                                data[l][2] = Integer.parseInt(v[2]);
                                data[l][3] = Integer.parseInt(v[3]);
                                data[l][4] = Integer.parseInt(v[4]);
                                data[l][5] = Integer.parseInt(v[5]);
                                data[l][6] = Integer.parseInt(v[6]);
                                data[l][7] = Integer.parseInt(v[7]);
                                data[l][8] = Integer.parseInt(v[8]);
                                data[l][9] = Integer.parseInt(v[9]);
                                i++;
                            }
                            
                        }
                        sir1.set_availability(data);
                        int[][] b;
                        b = sir1.get_availabilty();
                        for (int l = 0; l < 5; l++)
                        {
                                System.out.println(b[l][0] + "   " +b[l][1] + "   " 
                                        +b[l][2] + "   " +b[l][3] + "   " +b[l][4] 
                                        + "   " +b[l][5] + "   " +b[l][6] + "   " +b[l][7] 
                                        + "   " +b[l][8] + "   " +b[l][9] + "   " );  
                        }
                        
                        
                    }
                    else if (h[0].equals("time_slots"))
                    {
                        sir1.head = new lecturer.slots[5];
                        i++;
                        while(!lines.get(i).equals("end"))
                        {
                            String d = lines.get(i);
                            String f[] = d.split(":");
                            if (f[0].equals("day1"))
                            {
                                i++;
                                
                                while(!lines.get(i).equals("endslot"))
                                {
                                   String s = lines.get(i);
                                   String g[] = s.split(" ");
                                   lecturer.slots slot = new lecturer.slots();
                                   slot.start_time = g[0];
                                   slot.end_time = g[1];
                                   slot.next = null;
                                   sir1.add_slot(slot, 0);
                                    i++;
                                }
                            }
                            else if (f[0].equals("day2"))
                            {
                                i++;
                                
                                while(!lines.get(i).equals("endslot"))
                                {
                                   String s = lines.get(i);
                                   String g[] = s.split(" ");
                                   lecturer.slots slot = new lecturer.slots();
                                   slot.start_time = g[0];
                                   slot.end_time = g[1];
                                   slot.next = null;
                                   sir1.add_slot(slot, 1);
                                    i++;
                                }
                            }
                            else if (f[0].equals("day3"))
                            {
                                i++;
                                
                                while(!lines.get(i).equals("endslot"))
                                {
                                   String s = lines.get(i);
                                   String g[] = s.split(" ");
                                   lecturer.slots slot = new lecturer.slots();
                                   slot.start_time = g[0];
                                   slot.end_time = g[1];
                                   slot.next = null;
                                   sir1.add_slot(slot, 2);
                                    i++;
                                }
                            }
                            else if (f[0].equals("day4"))
                            {
                                i++;
                                
                                while(!lines.get(i).equals("endslot"))
                                {
                                   String s = lines.get(i);
                                   String g[] = s.split(" ");
                                   lecturer.slots slot = new lecturer.slots();
                                   slot.start_time = g[0];
                                   slot.end_time = g[1];
                                   slot.next = null;
                                   sir1.add_slot(slot, 3);
                                    i++;
                                }
                            }
                            else if (f[0].equals("day5"))
                            {
                                i++;
                                
                                while(!lines.get(i).equals("endslot"))
                                {
                                   String s = lines.get(i);
                                   String g[] = s.split(" ");
                                   lecturer.slots slot = new lecturer.slots();
                                   slot.start_time = g[0];
                                   slot.end_time = g[1];
                                   slot.next = null;
                                   sir1.add_slot(slot, 4);
                                    i++;
                                }
                            }
                            i++;
                        }
                        
                    }
                    i++;
                }
                i++;
                collectionOfLecturers.list_of_lecturers.add(sir1);

            }  // end of adding lecturers
            
            
            {
                    String p = lines.get(i);
                    String h[] = p.split(":");
                    if (h[0].equals("numberofroom")) 
                    {
                        x.set_num_of_room(Integer.parseInt(h[1]));
                        System.out.println("numberofroom  " + x.get_num_of_room());
                    }
            }   // end of initializing number of rooms
            
            i++;
            
            
            for (int k = 0; k < x.get_num_of_room(); k++)
            {
                Room room = new Room();
                int[][] data = new int[5][10];
                 while(!lines.get(i).equals("end"))
                {
                    String p = lines.get(i);
                    String h[] = p.split(":");

                    if(h[0].equals("roomname"))
                    {
                        room.set_roomname(h[1]);
                        System.out.println("roomname  " + room.get_roomname());
                    }
                    else if (h[0].equals("capacity"))
                    {
                        room.set_capacity(Integer.parseInt(h[1]));
                        System.out.println("capacity  " + room.get_capacity());
                    }
                    else if (h[0].equals("location"))
                    {
                        room.set_location(h[1]);
                        System.out.println("location  " + room.get_location());
                    }
                    else if(h[0].equals("availability"))
                    {
                        i++;
                        while(!lines.get(i).equals("end"))
                        {
                            for (int l = 0; l < 5; l++) 
                            {
                                String d = lines.get(i);
                                String v[] = d.split(":");
                                
                                data[l][0] = Integer.parseInt(v[0]);
                                data[l][1] = Integer.parseInt(v[1]);
                                data[l][2] = Integer.parseInt(v[2]);
                                data[l][3] = Integer.parseInt(v[3]);
                                data[l][4] = Integer.parseInt(v[4]);
                                data[l][5] = Integer.parseInt(v[5]);
                                data[l][6] = Integer.parseInt(v[6]);
                                data[l][7] = Integer.parseInt(v[7]);
                                data[l][8] = Integer.parseInt(v[8]);
                                data[l][9] = Integer.parseInt(v[9]);
                                i++;
                            }
                            
                        }
                        room.set_availability(data);
                        int[][] b;
                        b = room.get_availability();
                        for (int l = 0; l < 5; l++)
                        {
                                System.out.println(b[l][0] + "   " +b[l][1] + "   " 
                                        +b[l][2] + "   " +b[l][3] + "   " +b[l][4] 
                                        + "   " +b[l][5] + "   " +b[l][6] + "   " +b[l][7] 
                                        + "   " +b[l][8] + "   " +b[l][9] + "   " );  
                        }
                        
                        
                    }
                    i++;
                }
                 i++;
                 x.list_of_rooms.add(room);
            }   // end of adding rooms
            

            
        }
        catch (IOException ex) 
        {
            Logger.getLogger(OliveSyncTT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void initialise_colony(Colony solution_colony, CollectionOfClass collectionOfClass,
            Random rand, CollectionOfLecturers collectionOfLecturers, CollectionOfRoom collectionOfRoom)
    {
        TimeTable currentTT;
        TimeTable laggingTT;
        TimeTable test_tube;
        
        int solution, cur_room, day, hour;
        int final_population_size;
        
        solution_colony.set_population_size(0);
        
        final_population_size = max_population_size;
        
        while (solution_colony.get_population_size() < final_population_size)
        {    
            test_tube = new TimeTable();
            
            if (test_tube.NextTimeTable != null)
            {
                test_tube.NextTimeTable = null;
            }
            
            
            if ( solution_colony.last_time_table.NextTimeTable != null )
            {
                solution_colony.last_time_table.NextTimeTable = null;
            }
            
            for (cur_room = 0; cur_room < max_room_num; cur_room++) 
            {
                for (day = 0; day < days_in_week; day++)
                {
                    for (hour = 0; hour < hour_in_class; hour++)
                    {
                        test_tube.booking[cur_room][day][hour] = 0;
                    }
                }
            }
            
            repair_strategy_0(test_tube, collectionOfClass, rand);
            calculate_cost(test_tube, collectionOfClass, collectionOfLecturers, collectionOfRoom);
            if (solution_colony.population_size == 0) 
            {
                solution_colony.first_time_table = test_tube;
                solution_colony.last_time_table = test_tube;
                solution_colony.population_size++;
            }
            else 
            {
                currentTT = solution_colony.first_time_table;
                if (currentTT.cost >= test_tube.cost) 
                {
                    test_tube.NextTimeTable = currentTT;
                    solution_colony.first_time_table = test_tube;
                    solution_colony.population_size++;
                }
                else 
                {
                    currentTT = solution_colony.first_time_table;
                    while ((currentTT.cost <= test_tube.cost) && (currentTT.NextTimeTable != null))
                    {
                        if (currentTT.NextTimeTable != null) 
                        {
                            currentTT = currentTT.NextTimeTable;
                        }
                    };
                    
                    if (currentTT == solution_colony.last_time_table) 
                    {
                        solution_colony.last_time_table = test_tube;
                        test_tube.NextTimeTable = null;
                    }
                    else 
                    {
                        test_tube.NextTimeTable = currentTT.NextTimeTable;
                    }
                    currentTT.NextTimeTable = test_tube;
                    solution_colony.population_size++;
                    
                }
            }
            solution_colony.last_time_table.NextTimeTable = null;
            currentTT = solution_colony.first_time_table;
            while (currentTT != null)
            {
                currentTT = currentTT.NextTimeTable;
            }
            test_tube = null;
        }
    }   // end of initialise function
    
    public static void repair_strategy_0(TimeTable curr_tt, CollectionOfClass collectionOfClass, Random random)
    {
        int curr_class;
        int class_occured;
        int curr_room;
        int day;
        int hour;
        int one_to_remove;
        char chr;
        
        for (curr_class = 1; curr_class <= collectionOfClass.get_num_of_class(); curr_class++)
        {
            class_occured = 0;
            for (curr_room = 0;curr_room < max_room_num; curr_room++)
            {
                for (day = 0; day < days_in_week; day++)
                {
                    for (hour = 0; hour < hour_in_class; hour++)
                    {
                        if (curr_tt.booking[curr_room][day][hour] == curr_class)
                        {
                            class_occured++;
                        }
                    }
                }
            }
            
            if (class_occured == 0) 
            {
                while (class_occured == 0 )
                {         
                    
                    curr_room = random.nextInt(max_room_num);
                    day = random.nextInt(days_in_week);
                    hour = random.nextInt(hour_in_class);
                    
                    if (curr_tt.booking[curr_room][day][hour] == 0)
                    {
                        curr_tt.booking[curr_room][day][hour] = curr_class;
                        class_occured++;
                    }
                }
            }
        }
    }   // end of repair_strategy_0 function , (assigns the class to any room randomly)
    
    public static void calculate_cost(TimeTable a, CollectionOfClass collectionOfClass, CollectionOfLecturers collectionOfLecturers , CollectionOfRoom collectionOfRoom)
    {
        long the_cost = 0;
        long problem1;
        long problem2;
        long problem3;
        long problem4;

        a.cost = 0;
        a.rcc_error_cost = related_classes(a, collectionOfClass);
        a.rts_error_cost = room_too_small(a, collectionOfRoom, collectionOfClass);
        a.ldb_error_cost = lecturer_double_booked(a, collectionOfLecturers, collectionOfClass);
        a.lua_error_cost = lecturer_unavailable(a, collectionOfClass, collectionOfLecturers);
        
        problem1 = a.rcc_error_cost*cost_of_related_class_clash;
        problem2 = a.rts_error_cost*cost_of_room_too_small;
        problem3 = a.ldb_error_cost*cost_of_lecturer_doubly_booked;
        problem4 = a.lua_error_cost*cost_of_unavailable_lecturer;
        the_cost = problem1 + problem2 + problem3 + problem4;
        a.cost = the_cost;
        //System.out.println(a.cost);
    }   // end of calculate_cost function, (returns the cost of error in timetable)
    
    public static long related_classes(TimeTable curr_ptr, CollectionOfClass collectionOfClass)
    {
        int num_of_occurences = 0;
        int curr_class_group = 0;
        int this_group_has_occured = 0;
        int curr_room;
        int curr_day;
        int hour;
        int this_class_group;
        int this_class_num;
        
        for(curr_class_group = 1; 
                curr_class_group <=collectionOfClass.get_num_of_class_association();
                curr_class_group++)
        {
            for(curr_day = 0; curr_day < days_in_week; curr_day++)
            {
                for(hour = 0; hour < hour_in_class; hour++)
                {
                    this_group_has_occured = 0;
                    for(curr_room = 0; curr_room < max_room_num; curr_room++)
                    {
                        this_class_num = curr_ptr.booking[curr_room][curr_day][hour];
                        if (this_class_num != 0)
                        {
                            this_class_group = collectionOfClass.list_of_class.get(this_class_num).get_associated_code_of_class();
                            if (this_class_group == curr_class_group)
                            {
                                this_group_has_occured++;
                            }
                        }
                    }
                    if (this_group_has_occured > 1)
                    {
                        num_of_occurences += (this_group_has_occured-1);
                    }
                }
            }
        }
        return num_of_occurences;
    }  // end of related classes (number of occurences of related class at same time and same day)
    
    public static long room_too_small(TimeTable curr_ptr, CollectionOfRoom collectionOfRoom, CollectionOfClass collectionOfClass)
    {
        int num_of_occurences = 0;
        int curr_room;
        int curr_size_available;
        int curr_size_allocated;
        int curr_day;
        int curr_class;
        int hour;
        
        for (curr_room = 0; curr_room < collectionOfRoom.get_num_of_room(); curr_room++)
        {
            curr_size_available = collectionOfRoom.list_of_rooms.get(curr_room).get_capacity();
            
            for (curr_day = 0; curr_day < days_in_week; curr_day++)
            {
                for (hour = 0; hour < hour_in_class; hour++)
                {
                    curr_class = curr_ptr.booking[curr_room][curr_day][hour];
                    
                    if (curr_class != 0)
                    {
                        curr_size_allocated = collectionOfClass.list_of_class.get(curr_class).get_size_of_class();
                        
                        if (curr_size_available < curr_size_allocated)
                        {
                            num_of_occurences++;
                        }
                    }
                    
                }
            }
        }
        return num_of_occurences;
    }  //  end of room_too_small function (if room capacity is less than required capacity gives number of such instances)
    
    
    
    public static long lecturer_double_booked(TimeTable timeTable, CollectionOfLecturers collectionOfLecturers, CollectionOfClass collectionOfClass)
    {
        int num_of_occurences = 0;
        int curr_room;
        int lecturer_num;
        int curr_lecturer;
        int num_of_bookings_at_this_time;
        int curr_lecturer_is;
        TClass current_class_is;
        int curr_class;
        int curr_class_num;
        int curr_day;
        int hour;
        
        for (lecturer_num = 0;lecturer_num < collectionOfLecturers.get_num_of_lecturer(); lecturer_num++) 
        {
            for (curr_day = 0; curr_day < days_in_week; curr_day++)
            {
                for ( hour = 0; hour < hour_in_class; hour++ ) 
                {
                    num_of_bookings_at_this_time = 0;
                    for (curr_room = 0; curr_room < max_room_num; curr_room++) 
                    {
                        curr_class_num = timeTable.booking[curr_room][curr_day][hour];
                        if (curr_class_num != 0)
                        {
                            curr_lecturer = collectionOfClass.list_of_class.get(curr_class_num).get_reference_of_lecturers();
                            if (lecturer_num == curr_lecturer)
                            {
                                num_of_bookings_at_this_time++;
                            }
                        }
                        if (num_of_bookings_at_this_time > 1)
                        {
                            num_of_occurences += (num_of_bookings_at_this_time-1);
                        }
                    }
                }
            }
        }
        return num_of_occurences;
    }  //  end of lecturer_doubly_booked function, (if a lecturer is booked twice at same day and time 
       //                                            counts number of such instances )
    
    public static long lecturer_unavailable(TimeTable curr_ptr, CollectionOfClass collectionOfClass, CollectionOfLecturers collectionOfLecturers)
    {
        int num_of_occurences = 0;
        int curr_room;
        int lecturer_num;
        int curr_lecturer;
        int current_lecturer_is;
        TClass current_class_in;
        int curr_class;
        int curr_class_num;
        int curr_day;
        int hour;
        
        for (curr_day = 0 ; curr_day < days_in_week; curr_day++)
        {
            for (hour = 0; hour < hour_in_class; hour++)
            {
                for (curr_room = 0; curr_room < max_room_num; curr_room++) 
                {
                    curr_class_num = curr_ptr.booking[curr_room][curr_day][hour];
                    //System.out.println(curr_class_num + "    class num ");
                    if (curr_class_num != 0)
                    {
                        curr_lecturer = collectionOfClass.list_of_class.get(curr_class_num).get_reference_of_lecturers();
                        //System.out.println(curr_lecturer + "   lecturer ");
                        //System.out.println(collectionOfLecturers.list_of_lecturers.get(curr_lecturer).availability[curr_day][hour]);
                        if (collectionOfLecturers.list_of_lecturers.get(curr_lecturer).availability[curr_day][hour] == 0) 
                        {
                            num_of_occurences++;
                        }
                    }
                }
            }
        }
        return num_of_occurences;
    }   //  end of lecturer_unavailable function ( returns number of instances
        //                                          such that lecturer is unavailable for that class on that day and that time )
    
    public static void breed_colony(Colony solution_colony, CollectionOfClass collectionOfClass, CollectionOfLecturers collectionOfLecturers, CollectionOfRoom collectionOfRoom)
    {
        int cross_over_rate = 2;
        TimeTable mother;
        TimeTable father;
        TimeTable test_tube;
        TimeTable curr_ptr;
        
        int mother_pos;
        int father_pos;
        int a;
        int curr_room;
        int day;
        int hour;
        int state = 0;
        Random random = new Random();
        mother_pos = random.nextInt(solution_colony.get_population_size());
        do 
        {            
            father_pos = random.nextInt(solution_colony.get_population_size());
        } 
        while (father_pos == mother_pos);
        
        mother = solution_colony.first_time_table;
        for (a = 0; a < mother_pos; a++) 
        {
            mother = mother.NextTimeTable;
        }
        
        father = solution_colony.first_time_table;
        for (a = 0; a < father_pos; a++) 
        {
            father = father.NextTimeTable;
        }
        
        test_tube = new TimeTable();
        state = random.nextInt(2)+1;
        
        for (day = 0; day < days_in_week; day++)
        {
            for (hour = 0; hour < hour_in_class; hour++)
            {
                for (curr_room = 0; curr_room < max_room_num; curr_room++)
                {
                    if (state == 1)
                    {
                        test_tube.booking[curr_room][day][hour] = mother.booking[curr_room][day][hour];
                        if (random.nextInt(cross_over_rate) == 0)
                        {
                            state = 2;
                        }
                    }
                    else 
                    {
                        test_tube.booking[curr_room][day][hour] = father.booking[curr_room][day][hour];
                        if (random.nextInt(cross_over_rate) == 0)
                        {
                            state = 1;
                        }
                    }
                }
            }
        }
        mutate(test_tube);
        repair_strategy(test_tube);
        repair_strategy_0(test_tube, collectionOfClass, random);
        calculate_cost(test_tube, collectionOfClass,collectionOfLecturers,collectionOfRoom);
        {
            curr_ptr = solution_colony.first_time_table;
            if (curr_ptr.cost >= test_tube.cost)
            {
                test_tube.NextTimeTable = curr_ptr;
                solution_colony.first_time_table = test_tube;
                solution_colony.population_size++;
            }
            else 
            {
                curr_ptr = solution_colony.first_time_table;
                calculate_cost(curr_ptr.NextTimeTable, collectionOfClass, collectionOfLecturers, collectionOfRoom);
                //if (curr_ptr.NextTimeTable.cost != 0) System.out.println("yooo");
                
                while ((curr_ptr.NextTimeTable.cost <= test_tube.cost) && (curr_ptr.NextTimeTable != null))
                {
                    if (curr_ptr.NextTimeTable != null)
                    {
                        curr_ptr = curr_ptr.NextTimeTable;
                    }
                    if (curr_ptr.NextTimeTable == null) break;
                }
                if (curr_ptr == solution_colony.last_time_table)
                {
                    solution_colony.last_time_table = test_tube;
                    test_tube.NextTimeTable = null;}
                else
                {
                    test_tube.NextTimeTable = curr_ptr.NextTimeTable;
                }
                curr_ptr.NextTimeTable = test_tube;
                solution_colony.population_size++;
            }
        }
    }   // end of breed_colony function
    
    public static void repair_strategy(TimeTable curr_ptr)
    {
        Random random = new Random();
        class booking_location
        {
            int which_room;
            int which_day;
            int which_hour;
            booking_location next;
        }
        
        booking_location first = new booking_location();
        booking_location curr_booking = new booking_location();
        booking_location booking_to_remove = new booking_location();
        int cur_class;
        int class_occured;
        int cur_room;
        int day;
        int hour;
        int one_to_remove;
        int a = 0;
        
        for (cur_class = 1; cur_class < max_num_of_classes; cur_class++)
        {
            class_occured = 0;
            for (cur_room = 0; cur_room < max_room_num; cur_room++)
            {
                for (day = 0; day < days_in_week; day++)
                {
                    for (hour = 0; hour < hour_in_class; hour++)
                    {
                        if ( curr_ptr.booking[cur_room][day][hour]==cur_class )
                        {
                            class_occured++;
                            curr_booking.which_room = cur_room;
                            curr_booking.which_day = day;
                            curr_booking.which_hour = hour;
                            if (class_occured==1) 
                            {
                                first = curr_booking;
                                //curr_booking = first;
                            }
                            else 
                            {
                                curr_booking.next = new booking_location();
                                curr_booking = curr_booking.next;
                            }
                        
                            
                        }
                    }
                }
            }
            if (class_occured == 1)
            {
                first = null;
            }
            else
            {
                if (class_occured > 1)
                {
                    while (class_occured > 1)
                    {
                        curr_booking = first;
                        one_to_remove = random.nextInt(class_occured);
                        for (a = 0; a < one_to_remove; a++ )
                        {
                            curr_booking = curr_booking.next;
                        }
                        cur_room = curr_booking.which_room;
                        day = curr_booking.which_day;
                        hour = curr_booking.which_hour;
                        if (curr_ptr.booking[cur_room][day][hour]==cur_class)
                        {
                            curr_ptr.booking[cur_room][day][hour] = 0;
                            if (one_to_remove == 0)
                            {
                                booking_to_remove = first;
                                first = first.next;
                            }
                            else 
                            {
                                if (one_to_remove != class_occured)
                                {
                                    curr_booking = first;
                                    for ( a = 0; a < (one_to_remove-1); a++ )
                                    {
                                        curr_booking = curr_booking.next;
                                    }
                                    booking_to_remove = curr_booking.next;
                                    curr_booking.next = (curr_booking.next).next;
                                    
                                }
                            }
                            booking_to_remove = null;
                            class_occured--;
                        }
                        
                    }
                    first = null;
                }
            }
        }
        
    }    // end of repair_strategy function
    
    public static void kill_costly_colony_members(Colony solution_colony)
    {
        int amount_to_kill;
        double kill_ratio = 0.5; // need not be integer
        TimeTable curr_ptr;
        TimeTable  lagging_ptr;
        int a;
        
        amount_to_kill = (int)((solution_colony.population_size)*kill_ratio);
        while ((solution_colony.population_size-amount_to_kill) < 2)
        {
            amount_to_kill--;
        }
        curr_ptr = solution_colony.first_time_table;
        for (a = 0; a < (solution_colony.population_size-amount_to_kill)-1; a++ )
        {
            curr_ptr = curr_ptr.NextTimeTable;
        }
        solution_colony.last_time_table = curr_ptr;
        curr_ptr = curr_ptr.NextTimeTable;
        solution_colony.last_time_table.NextTimeTable = null;
        do
        {
            lagging_ptr = curr_ptr;
            curr_ptr = curr_ptr.NextTimeTable;
            lagging_ptr = null;
            solution_colony.population_size--;
        }
        while(curr_ptr != null);
        
    }  //  end of kill_costly_colony_members()
    
    public static void find_average_cost(Colony colony)
    {
        long sum_of_costs = 0;
        long sum_of_error1 = 0;
        long sum_of_error2 = 0;
        long sum_of_error3 = 0;
        long sum_of_error4 = 0;
        TimeTable curr_ptr;
        long a;
        
        curr_ptr = colony.first_time_table;
        a = 0;
        while (curr_ptr != null)
        {
            a++;
            sum_of_costs += curr_ptr.cost;
            sum_of_error1 += curr_ptr.rcc_error_cost;
            sum_of_error2 += curr_ptr.rts_error_cost;
            sum_of_error3 += curr_ptr.ldb_error_cost;
            sum_of_error4 += curr_ptr.lua_error_cost;
            curr_ptr = curr_ptr.NextTimeTable;
        }
        
        colony.avg_cost = (sum_of_costs/(colony.population_size));
        colony.rcc_error_cost = (sum_of_error1/(colony.population_size));
        colony.rts_error_cost = (sum_of_error2/(colony.population_size));
        colony.ldb_error_cost = (sum_of_error3/(colony.population_size));
        colony.lua_error_cost = (sum_of_error4/(colony.population_size));
        
//        System.out.println(" the average cost of timetable colony is  " + colony.avg_cost);
    }  // end of average cost
    
    public static void mutate(TimeTable curr_ptr)
    {
        int temporary;
        int cur_room;
        int cur_day;
        int cur_hour;
        int random_room;
        int random_day;
        int random_hour;
        
        Random random = new Random();
        
        for ( cur_room = 0; cur_room < max_room_num; cur_room++)
        {
            for (cur_day = 0; cur_day < days_in_week; cur_day++)
            {
                for (cur_hour = 0; cur_hour < hour_in_class; cur_hour++)
                {
                    if ( (random.nextInt(1000)+1) < mutation_rate )
                    {
                        temporary = curr_ptr.booking[cur_room][cur_day][cur_hour];
                        random_room = random.nextInt(max_room_num);
                        random_hour = random.nextInt(hour_in_class);
                        random_day = random.nextInt(days_in_week);
                        
                        curr_ptr.booking[cur_room][cur_day][cur_hour] = curr_ptr.booking[random_room][random_day][random_hour];
                        curr_ptr.booking[random_room][random_day][random_hour] = temporary;
                    }
                }
            }
        }     
        
    }   // end of mutate time table function ( randomly changes the class associated with a room, day and time )
    
    public static void output(Colony colony)
    {
        TimeTable timeTable;
       
        timeTable = colony.first_time_table;
        
        while (timeTable != null)
        {            
            output(timeTable);
            timeTable = timeTable.NextTimeTable;
        }
    }
    
    // input shpuld of form (x,y,x,y,x,y) first day start time, end time and so on
    public static void teacher_availablility_creator(float a, float b, float c, float d,
                                                float e, float f, float g, float h,
                                                float i, float j)
    {
        
    }
    
    public static boolean compare_slot(String a, String b, String c, String d)
    {
        if (Integer.parseInt(a.split(":")[0]) < Integer.parseInt(c.split(":")[0]) )
        {
            if (Integer.parseInt(b.split(":")[0]) > Integer.parseInt(d.split(":")[0]) )
            {
                return true;
            }
            else if (Integer.parseInt(b.split(":")[0]) == Integer.parseInt(d.split(":")[0]) )
            {
                if (Integer.parseInt(b.split(":")[1]) > Integer.parseInt(d.split(":")[1]) ) 
                {
                    return true;
                }
            }
            return false;
        }
        else if (Integer.parseInt(a.split(":")[0]) == Integer.parseInt(c.split(":")[0]) )
        {
            if ( Integer.parseInt(a.split(":")[1]) < Integer.parseInt(c.split(":")[1]) )
            {
                if (Integer.parseInt(b.split(":")[0]) > Integer.parseInt(d.split(":")[0]) )
                {
                    return true;
                }
                else if (Integer.parseInt(b.split(":")[0]) == Integer.parseInt(d.split(":")[0]) )
                {
                    if (Integer.parseInt(b.split(":")[1]) > Integer.parseInt(d.split(":")[1]) ) 
                    {
                        return true;
                    }
                }
                return false;
            }
            else 
            {
                return false;
            }
        }
        else return false;
    }
    
    public static void upload_data(Colony colony)
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/TimeTable?" + "user=rathod&password=hyunbin7";
            Connection con = DriverManager.getConnection(connectionUrl);
            
            Statement stmt = null;
            ResultSet rs = null;
            String SQL = "";
            
            stmt = con.createStatement();
            
            SQL = "TRUNCATE TABLE `solutionColony`";
            
            stmt.executeUpdate(SQL);
            
            TimeTable timeTable;
       
            timeTable = colony.first_time_table;
            int i = 0;
            int j = 1;
            while (timeTable != null)
            {
                SQL = "";
                
                for( i = 0 ; i < max_room_num ; i++ )
                {
                           SQL = "INSERT INTO `solutionColony` (`id`, `solution`, `roomid`, `day1slot1`,"
                    + " `day1slot2`, `day1slot3`, `day1slot4`, `day1slot5`, `day1slot6`, `day1slot7`,"
                    + " `day1slot8`, `day1slot9`, `day1slot10`, `day2slot1`, `day2slot2`, `day2slot3`,"
                    + " `day2slot4`, `day2slot5`, `day2slot6`, `day2slot7`, `day2slot8`, `day2slot9`, "
                    + "`day2slot10`, `day3slot1`, `day3slot2`, `day3slot3`, `day3slot4`, `day3slot5`, "
                    + "`day3slot6`, `day3slot7`, `day3slot8`, `day3slot9`, `day3slot10`, `day4slot1`, "
                    + "`day4slot2`, `day4slot3`, `day4slot4`, `day4slot5`, `day4slot6`, `day4slot7`, "
                    + "`day4slot8`, `day4slot9`, `day4slot10`, `day5slot1`, `day5slot2`, `day5slot3`, "
                    + "`day5slot4`, `day5slot5`, `day5slot6`, `day5slot7`, `day5slot8`, `day5slot9`, "
                    + "`day5slot10`) VALUES "
                    + "("+j+"," + max_population_size + ","+ max_room_num + "," +timeTable.booking[i][0][0] + ","+timeTable.booking[i][0][1] + ","
                    + timeTable.booking[i][0][2] + ","+timeTable.booking[i][0][3] + ","+timeTable.booking[i][0][4] + ","
                    + timeTable.booking[i][0][5] + ","+timeTable.booking[i][0][6] + ","+timeTable.booking[i][0][7] + ","
                    + timeTable.booking[i][0][8] + ","+timeTable.booking[i][0][9] + ","+timeTable.booking[i][1][0] + ","
                    + timeTable.booking[i][1][1] + ","+timeTable.booking[i][1][2] + ","+timeTable.booking[i][1][3] + ","
                    + timeTable.booking[i][1][4] + ","+timeTable.booking[i][1][5] + ","+timeTable.booking[i][1][6] + ","
                    + timeTable.booking[i][1][7] + ","+timeTable.booking[i][1][8] + ","+timeTable.booking[i][1][9] + ","
                    + timeTable.booking[i][2][0] + ","+timeTable.booking[i][2][1] + ","+timeTable.booking[i][2][2] + ","
                    + timeTable.booking[i][2][3] + ","+timeTable.booking[i][2][4] + ","+timeTable.booking[i][2][5] + ","
                    + timeTable.booking[i][2][6] + ","+timeTable.booking[i][2][7] + ","+timeTable.booking[i][2][8] + ","
                    + timeTable.booking[i][2][9] + ","+timeTable.booking[i][3][0] + ","+timeTable.booking[i][3][1] + ","
                    + timeTable.booking[i][3][2] + ","+timeTable.booking[i][3][3] + ","+timeTable.booking[i][3][4] + ","
                    + timeTable.booking[i][3][5] + ","+timeTable.booking[i][3][6] + ","+timeTable.booking[i][3][7] + ","
                    + timeTable.booking[i][3][8] + ","+timeTable.booking[i][3][9] + ","+timeTable.booking[i][4][0] + ","
                    + timeTable.booking[i][4][1] + ","+timeTable.booking[i][4][2] + ","+timeTable.booking[i][4][3] + ","
                    + timeTable.booking[i][4][4] + ","+timeTable.booking[i][4][5] + ","+timeTable.booking[i][4][6] + ","
                    + timeTable.booking[i][4][7] + ","+timeTable.booking[i][4][8] + ","+timeTable.booking[i][4][9] + ")";
                          int h = stmt.executeUpdate(SQL);
                    
                    
                    j++;
                }
                
                timeTable = timeTable.NextTimeTable;
            }
 
            
        }
        catch (ClassNotFoundException classNotFoundException) 
        {
            System.out.println("ClassNotFoundException : " + classNotFoundException.toString());
        } catch (SQLException ex)
        {
            Logger.getLogger(OliveSyncTT.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SQL Exception: " + ex.toString());
        }
        
    }
    
    public static void main(String[] args) 
    {
        int generations = 0;
        int num_of_generations = 0;
        long maximum_allowed_cost = 0;
        String j = "input1.txt";
        
        Random random = new Random();
        CollectionOfClass collectionOfClass = new CollectionOfClass();
        CollectionOfLecturers collectionOfLecturers = new CollectionOfLecturers();
        CollectionOfRoom collectionOfRoom = new CollectionOfRoom();
        Colony solution_colony = new Colony();
        initialise_constraints(j, collectionOfClass, collectionOfLecturers, collectionOfRoom);
        System.out.println("finished reading of input from text file");
        int population_size = max_population_size;
        initialise_colony(solution_colony, collectionOfClass, random, collectionOfLecturers, collectionOfRoom);
        find_average_cost(solution_colony);
        while (solution_colony.first_time_table.cost > maximum_allowed_cost)
        {
            kill_costly_colony_members(solution_colony);
            find_average_cost(solution_colony);
            
            while (solution_colony.population_size < max_population_size)
            {                
                num_of_trails++;
                breed_colony(solution_colony, collectionOfClass, collectionOfLecturers, collectionOfRoom);
            }
            num_of_generations++;
            System.out.println(" average cost of colony is  " + solution_colony.avg_cost);
            System.out.println(" number of trails is  " + num_of_trails);
            System.out.println(" cost of first time-table of colony is  " + solution_colony.first_time_table.cost);
            num_of_trails = 0;
        }
        
        System.out.println( "generations is " + num_of_generations);
        output(solution_colony);
        upload_data(solution_colony);
        GetDay day = new GetDay();
        System.out.println(day.currentDay("14/11/1997"));
          String slot = "8:30 9:30";
          String ds[] = slot.split(" ");
          System.out.println(ds[0].split(":")[0]+ "   " + ds[0].split(":")[1] + "    " + ds[1].split(":")[0] + "     " + ds[1].split(":")[1] );
          if (compare_slot("8:30", "10:30","9:01", "10:01"))
          {
              System.out.println("hello world");
          }
    }

    
}
