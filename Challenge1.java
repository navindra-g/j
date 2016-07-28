import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Challenge1
{
  private List<Date> data;
  SimpleDateFormat df;

  public Challenge1()
  {
    df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  }

  private Date parseDate(String s) throws ParseException
  {
    return df.parse(s);
  }

  private void readData(String fileName) throws IOException, ParseException
  {
    data = new ArrayList<Date>(); 
    BufferedReader reader = new BufferedReader(new FileReader(fileName));
    try
    {
      String line;
      while ((line = reader.readLine()) != null)
      {
        Date d = df.parse(line);
        System.out.println(df.format(d));
        data.add(d);
      }
    }
    finally
    {
      reader.close();
    }
  }

  private boolean isHourInRange(Date d, Date begin, Date end)
  {
      return true;
  }
  
  public int predict(Date date)
  {
      ArrayList<Integer> hourCounts = new ArrayList<Integer>();
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      cal.add(Calendar.HOUR, 1);
      Date endTime = cal.getTime();
      for (Iterator<Date> i = data.iterator(); i.hasNext();)
      {
          boolean inRange = false;
          while (i.hasNext() && !inRange)
          {
              inRange = isHourInRange(i.next(), date, endTime);
          }
          if (inRange)
          {
            int count = 1;
            while (i.hasNext() && inRange)
            {
                count++;
                inRange = isHourInRange(i.next(), date, endTime);
            }
            hourCounts.add(count);
          }
      }
      
      int sum = 0;
      for (Integer i : hourCounts)
      {
          sum += i.intValue();
      }
      return (hourCounts.size() > 0) ? (sum/hourCounts.size()) : 0;
  }

  public static void main(String[] args)
  {
    try
    {
      Challenge1 c = new Challenge1();
      c.readData("data.txt");
      if (args.length > 1)
      {
          System.out.println(c.predict(c.parseDate(args[1])));
      }
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
}