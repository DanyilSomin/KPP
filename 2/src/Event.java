import java.util.Comparator;

public class Event 
{
    static class DateComparator implements Comparator<Event>
    {
        @Override
        public int compare(Event e1, Event e2)
        {
            return Date.compare(e1.getDate(), e2.getDate());
        }
    }

    Event(String line) throws Throwable
    {
        String[] data = line.split(" ");

        if (data.length != 4)
        {
            throw new Throwable("Invalid event format: " + line);
        }

        int day = Integer.parseInt(data[1]);
        int month = Integer.parseInt(data[2]);
        int year = Integer.parseInt(data[3]);

        mName = new String(data[0]);
        mDate = new Date(day, month, year);
    }

    Event(Event e) throws Throwable
    {
        int day = e.mDate.getDay();
        int month = e.mDate.getMonth();
        int year = e.mDate.getYear();

        mName = new String(e.mName);
        mDate = new Date(day, month, year);
    }

    @Override
    public boolean equals(Object obj)
    {
		if (!(obj instanceof Event))
			return false;	
		if (obj == this)
			return true;
		return mName.equals(((Event) obj).mName) && mDate.equals(((Event) obj).mDate);
	}
 
    @Override
    public int hashCode()
    {
		return mName.length() * mDate.hashCode();
	}

    public String getName() { return mName; }
    public Date getDate() { return mDate; }

    public String getLine()
    {
        return String.format("%-20s", mName) + " " + mDate.getLine();
    }

    private String mName;
    private Date mDate;
}
