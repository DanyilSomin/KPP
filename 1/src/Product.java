public abstract class Product
{
    public Product(int price, String name)
    {
        mPrice = price;
        mName = name;
    }

    public int getPrice() { return mPrice; }
    public String getName() { return mName; }

    public abstract void print();

    protected int mPrice;
    protected String mName;
}
