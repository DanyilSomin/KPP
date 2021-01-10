public class Lamp extends Product
{
    public Lamp(int price, String name, int power, int lightTemperature)
    {
        super(price, name);
        
        mPower = power;
        mLightTemperature = lightTemperature;
    }

    public int getPower() { return mPower; }
    public int getLightTemperature() { return mLightTemperature; }

    @Override
    public void print() 
    {
        System.out.print("Lamp:\t\t" + mName + "\t\t" 
                         + mPrice + "UAH\t\t" 
                         + mPower + "W\t\t"
                         + mLightTemperature + "K\n");
    }

    protected int mPower;
    protected int mLightTemperature;
}
