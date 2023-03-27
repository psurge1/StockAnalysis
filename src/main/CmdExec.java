import java.io.IOException;



public class CmdExec {
    public static Process exec(String cmdAndFmt, Object ... args)
    {
        String s = String.format(cmdAndFmt, args);
        if (!((String) args[0]).equals("sma"))
        {
            System.out.println(s);
        }
        try
        {
            Process p = Runtime.getRuntime().exec(s);
            try
            {
                p.waitFor();
            }
            catch (InterruptedException e)
            {
                System.out.println(e);
            }
            return p;
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        return null;
    }

    public static Process exec(String cmdAndFmt, boolean wait, Object ... args)
    {
        String s = String.format(cmdAndFmt, args);
        try
        {
            Process p = Runtime.getRuntime().exec(s);
            if (wait)
            {
                try
                {
                    p.waitFor();
                }
                catch (InterruptedException e)
                {
                    System.out.println(e);
                }
            }
            return p;
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        return null;
    }
}