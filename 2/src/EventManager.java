import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class EventManager {
    public static void main(String[] args) throws Throwable
    {
        ArrayList<Event> eventsMain = readFromFile("../eventsMain.txt");
        System.out.println("Main:");
        for (Event event : eventsMain)
        {
            System.out.println(event.getLine());    
        }
        System.out.println(); 

        ArrayList<Event> eventsSecondary = readFromFile("../eventsSecondary.txt");
        System.out.println("Secondary:");
        for (Event event : eventsSecondary)
        {
            System.out.println(event.getLine());    
        }
        System.out.println(); 

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 
        while (true)
        {
            System.out.println("Task number 1, 2 or 3, or q to quit: ");
            String inLine = br.readLine();

            if (inLine.equals("q"))
            {
                br.close();
                break;
            }
            else if (inLine.equals("1") || inLine.equals("2"))
            {
                if (inLine.equals("2"))
                {
                    System.out.println("Before:");
                }

                Map<Integer, Set<Event>> map = new HashMap<Integer, Set<Event>>();

                for (Event event : eventsMain)
                {
                    int year = event.getDate().getYear();
                    if (map.containsKey(year))
                    {
                        map.get(year).add(event);
                    }
                    else
                    {
                        map.put(year, new TreeSet<Event>(new Event.DateComparator()));
                        map.get(year).add(event);
                    }
                }

                for (int key : map.keySet())
                {
                    System.out.println("Year: " + Integer.toString(key));

                    for (Event event : map.get(key))
                    {
                        System.out.println(event.getLine());
                    }
                    System.out.println();
                }

                if (inLine.equals("2"))
                {
                    System.out.println("After:");
                    for (int key : map.keySet())
                    {
                        ArrayList<Event> arr = new ArrayList<>();
                        for (Event event : map.get(key))
                        {
                            arr.add(new Event(event));
                        }

                        for (int i = 0; i < arr.size(); ++i)
                        {
                            for (int j = i + 1; j < arr.size(); ++j)
                            {
                                Date d1 = arr.get(i).getDate();
                                Date d2 = arr.get(j).getDate();
                                if (d1.getYear() == d2.getYear()
                                 && d1.getMonth() == d2.getMonth())
                                {
                                    if (d1.getDay() - d2.getDay() == 1
                                        || d1.getDay() - d2.getDay() == -1)
                                    {
                                        map.get(key).remove(arr.get(i));
                                        map.get(key).remove(arr.get(j));
                                    }
                                }
                            }
                        }
                    }

                    for (int key : map.keySet())
                    {
                        System.out.println("Year: " + Integer.toString(key));

                        for (Event event : map.get(key))
                        {
                            System.out.println(event.getLine());
                        }
                        System.out.println();
                    }
                }
            }
            else if (inLine.equals("3"))
            {
                ArrayList<Event> arr = new ArrayList<>();
                for (Event event : eventsMain)
                {
                    arr.add(new Event(event));
                }

                for (Event e : eventsMain)
                {
                    if (eventsSecondary.contains(e))
                    {
                        arr.remove(e);
                    }
                }

                System.out.println("Pairs: ");

                for (int i = 0; i < arr.size(); ++i)
                {
                    for (int j = i + 1; j < arr.size(); ++j)
                    {
                        if (Date.compare(arr.get(i).getDate(), arr.get(j).getDate()) != 0)
                        {
                            System.out.print(arr.get(i).getLine());
                            System.out.print(" - ");
                            System.out.print(arr.get(j).getLine());
                            System.out.println();
                        }
                    }
                }
            }

        }
    }

    static private ArrayList<Event> readFromFile(String filepath) throws Throwable
    {
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        ArrayList<Event> eventsMain = new ArrayList<Event>();

        String line = reader.readLine();
        while (line != null) 
        {
            eventsMain.add(new Event(line));

            line = reader.readLine();
        }
        reader.close();

        return eventsMain;
    }
}