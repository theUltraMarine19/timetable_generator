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
public class TimeTable 
{
    TimeTable NextTimeTable;
    int booking[][][];
    long cost;
    long rcc_error_cost;
    long rts_error_cost;
    long ldb_error_cost;
    long lua_error_cost;
    
    public TimeTable()
    {
        NextTimeTable = null;
        booking = new int[OliveSyncTT.max_room_num][OliveSyncTT.days_in_week][OliveSyncTT.hour_in_class];
    }
    
    
}
