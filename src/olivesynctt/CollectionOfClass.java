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
public class CollectionOfClass 
{
    List<TClass> list_of_class;
    int number_of_class;
    int number_of_class_association;
    
    public CollectionOfClass() 
    {
        number_of_class_association = 0;
        number_of_class = 0;
        list_of_class = new ArrayList<>(OliveSyncTT.max_num_of_classes);
    }
    
    public void set_num_of_class(int a)
    {
        number_of_class = a;
    }
    
    public int get_num_of_class()
    {
        return number_of_class;
    }  
    
    public void set_num_of_class_association(int a)
    {
        number_of_class_association = a;
    }
    
    public int get_num_of_class_association()
    {
        return number_of_class_association;
    }
    
    
    
    public void add_class(TClass z)
    {
        list_of_class.add(z);
        number_of_class++;
    }
    
    public TClass get_class(int i)
    {
        return list_of_class.get(i);
    }
}
