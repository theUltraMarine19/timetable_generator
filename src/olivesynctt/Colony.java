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
public class Colony 
{
    String name_of_colony;
    TimeTable first_time_table;
    TimeTable last_time_table;
    int population_size;
    
    long avg_cost;
    long rcc_error_cost;
    long rts_error_cost;
    long ldb_error_cost;
    long lua_error_cost;
    
    public Colony()
    {
        first_time_table = new TimeTable();
        last_time_table = new TimeTable();
    }
    
    public void set_name_of_colony(String h)
    {
        name_of_colony = h;
    }
    
    public String get_name_of_colony()
    {
        return name_of_colony;
    }
    
    public void set_first_table(TimeTable a)
    {
        first_time_table = a;
    }
    
    public TimeTable get_first_time_table()
    {
        return first_time_table;
    }
    
    public void set_second_table(TimeTable a)
    {
        last_time_table = a;
    }
    
    public TimeTable get_second_time_table()
    {
        return last_time_table;
    }
    
    public void set_population_size(int a)
    {
        population_size = a;
    }
    
    public int get_population_size()
    {
        return population_size;
    }
    
}
