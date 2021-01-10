public class Illuminator extends Product
{
    public Illuminator(
        int price,
        String name,
        int maxLampPower,
        int lampCount)
    {
        super(price, name);
        mMaxLampPower = maxLampPower;
        mLampCount = lampCount;
    }

    public int getLampCount() { return mLampCount; }
    public int getMaxLampPower() { return mMaxLampPower; }

    public int totalPower() { return mLampCount * mMaxLampPower; }

    @Override
    public void print() 
    {
        System.out.print("Illuminator:\t" + mName + "\t\t" 
                         + mPrice + "UAH\t\t" 
                         + mLampCount + " lamp(s)\t"
                         + mMaxLampPower + "W each\n");
    }

    protected int mMaxLampPower;
    protected int mLampCount;
}