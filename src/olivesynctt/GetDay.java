package olivesynctt;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author drathod
 */
public class GetDay
{
    Date date;
    SimpleDateFormat dateFormat ;
    
    public GetDay() 
    {
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }
    
    public boolean validate_date(String h)
    {
        String f[] = h.split("/");
        if (Integer.parseInt(f[2])%4 == 0) 
        {
            if (Integer.parseInt(f[1]) == 1) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 31) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }  // january
            else if (Integer.parseInt(f[1]) == 2) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 29) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }  // feb
            else if (Integer.parseInt(f[1]) == 3) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 31) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }   // march
            else if (Integer.parseInt(f[1]) == 4) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 30) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }   // april
            else if (Integer.parseInt(f[1]) == 5) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 31) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }  // may
            else if (Integer.parseInt(f[1]) == 6) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 30) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }  // june
            else if (Integer.parseInt(f[1]) == 7) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 31) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }   // july
            else if (Integer.parseInt(f[1]) == 8) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 31) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }   // august
            else if (Integer.parseInt(f[1]) == 9) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 30) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }   // september
            else if (Integer.parseInt(f[1]) == 10) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 31) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }  // october
            else if (Integer.parseInt(f[1]) == 11) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 30) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }   // november
            else if (Integer.parseInt(f[1]) == 12) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 31) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }   // december
        }  // leap year case
        else 
        {
            if (Integer.parseInt(f[1]) == 1) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 31) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }  // january
            else if (Integer.parseInt(f[1]) == 2) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 28) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }  // feb
            else if (Integer.parseInt(f[1]) == 3) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 31) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }   // march
            else if (Integer.parseInt(f[1]) == 4) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 30) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }   // april
            else if (Integer.parseInt(f[1]) == 5) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 31) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }  // may
            else if (Integer.parseInt(f[1]) == 6) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 30) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }  // june
            else if (Integer.parseInt(f[1]) == 7) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 31) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }   // july
            else if (Integer.parseInt(f[1]) == 8) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 31) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }   // august
            else if (Integer.parseInt(f[1]) == 9) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 30) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }   // september
            else if (Integer.parseInt(f[1]) == 10) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 31) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }  // october
            else if (Integer.parseInt(f[1]) == 11) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 30) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }   // november
            else if (Integer.parseInt(f[1]) == 12) 
            {
                if (Integer.parseInt(f[0]) >= 1 && Integer.parseInt(f[0]) <= 31) 
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }   // december
        }
        return false;
    }
    
    public String currentDay(String x)
    {
        if (validate_date(x))
        {
            String d = "";
            try 
            {
                date = dateFormat.parse(x);
                dateFormat.applyPattern("EEEE d MMM yyyy");
                d = dateFormat.format(date);
            }
            catch (ParseException ex)
            {
                Logger.getLogger(GetDay.class.getName()).log(Level.SEVERE, null, ex);
            }
            return d;
        }
        else 
        {
            return "invalid date";
        }
    }
    
}
