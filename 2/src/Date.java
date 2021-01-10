public class Date
{
    public static int compare(Date d1, Date d2)
    {
        if (d1.getYear() - d2.getYear() != 0)
        {
            return d1.getYear() - d2.getYear();
        }
        else if (d1.getMonth() - d2.getMonth() != 0)
        {
            return d1.getMonth() - d2.getMonth();
        }
        else
        {
            return d1.getDay() - d2.getDay();
        }
    }

    Date(int day, int month, int year) throws Throwable
    {
        if (day < 1 && day > 31)
        {
            throw new Throwable("Invalid day: " + Integer.toString(day));
        }

        if (month < 1 && month > 12)
        {
            throw new Throwable("Invalid day: " + Integer.toString(month));
        }

        mDay = day;
        mMonth = month;
        mYear = year;
    }

    @Override
    public boolean equals(Object obj)
    {
		if (!(obj instanceof Date))
			return false;	
		if (obj == this)
			return true;
        return mDay == ((Date) obj).mDay
         && mMonth == ((Date) obj).mMonth
         && mYear == ((Date) obj).mYear;
	}
 
    @Override
    public int hashCode()
    {
		return mDay * mMonth * mYear;
	}

    public int getDay() { return mDay; }
    public int getMonth() { return mMonth; }
    public int getYear() { return mYear; }

    public String getLine()
    {
        return new String(String.format("%02d", mDay) + " " 
             + String.format("%02d", mMonth) + " " 
             + String.format("%04d", mYear));
    }

    private int mDay;
    private int mMonth;
    private int mYear;
}
