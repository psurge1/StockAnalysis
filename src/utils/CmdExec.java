package utils;

import java.io.IOException;

public class CmdExec {
    public static Process exec(String cmdAndFmt, Object ... args)
    {
        String s = String.format(cmdAndFmt, args);
        try
        {
            return Runtime.getRuntime().exec(s);
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        return null;
    }
}