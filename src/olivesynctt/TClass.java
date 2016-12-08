/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package olivesynctt;

import jdk.nashorn.internal.codegen.types.Type;

/**
 *
 * @author drathod
 */
public class TClass 
{
    String name_of_class;
    int reference_of_lecturers;
    int size_of_class;
    int associated_code_of_class;
    String room_location;
    
    public void set_name(String j)
    {
        name_of_class = j;
    }
    
    public String get_name()
    {
        return name_of_class;
    }
    
    public void set_reference_of_lecturers(int a)
    {
        reference_of_lecturers = a;
    }
    
    public int get_reference_of_lecturers()
    {
        return reference_of_lecturers;
    }
    
    public void set_size_of_class(int a)
    {
        size_of_class = a;
    }
    
    public int get_size_of_class()
    {
        return size_of_class;
    }
    
    public void set_associated_code_of_class(int a)
    {
        associated_code_of_class = a;
    }
    
    public int get_associated_code_of_class()
    {
        return associated_code_of_class;
    }
    
    public void set_room_location(String j)
    {
        room_location = j;
    }
    
    public String get_room_location()
    {
        return room_location;
    }
    
}
