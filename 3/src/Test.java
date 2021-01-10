public class Test
{
    public static void doTest()
    {
        {
            String testStr1 = Main.doTask1("test.nottest");
            assert(testStr1.equals("test"));
        }

        {
            String testStr2 = Main.doTask1("test.test1.1test");
            assert(testStr2.equals("test"));
        }

        {
            String testStr3 = Main.doTask1("test test1.test.");
            assert(testStr3.equals("test1"));
        }

        {
            String testStr4 = Main.doTask1("test test1 test2 test3 test4 test5 test6.nottest. test testt1 test1 test2.test3. test test4 teas4 .test5");
            assert(testStr4.equals("test6"));
        }
    }
}
