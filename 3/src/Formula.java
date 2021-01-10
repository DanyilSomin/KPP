import java.util.ArrayList;

public class Formula {
    public int mStart;
    public int mEnd;
    public ArrayList<Integer> mVars;
    public int mOpenCount;
    public int mCloseCount;

    public Formula()
    {
        mStart = -1;
        mEnd = -1;
        mOpenCount = 0;
        mCloseCount = 0;

        mVars = new ArrayList<>();
    }

    public Formula(Formula f)
    {
        mStart = f.mStart;
        mEnd = f.mEnd;
        mOpenCount = f.mOpenCount;
        mCloseCount = f.mCloseCount;

        mVars = new ArrayList<>(f.mVars);
    }

    public boolean valid()
    {
        if (mStart >= mEnd) return false;
        if (mOpenCount != mCloseCount) return false;
        if (mVars.size() <= 0) return false;

        return true;
    }
}
