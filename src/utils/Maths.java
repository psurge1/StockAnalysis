package utils;

public class Maths {
    public static int IntFactorial(int n) throws OverflowException
    {
        int r = 1;
        for (; n > 0; --n)
        {
            r *= n;
            if (r < 0)
            {
                throw new OverflowException("Integer Overflow");
            }
        }
        return r;
    }

    public static long LongFactorial(long n) throws OverflowException
    {
        long r = 1;
        for (; n > 0; --n)
        {
            r *= n;
            if (r < 0)
            {
                throw new OverflowException("Long Overflow");
            }
        }
        return r;
    }

    public static int IntFactorialHandler(int n)
    {
        if (n < 0)
            return 0;
        int w = 0;
        boolean success = true;
        try {
            w = Maths.IntFactorial(n);
        }
        catch (OverflowException e) {
            System.out.println(e);
            success = false;
        }
        if (success)
                return w;
        return 0;
    }

    public static long LongFactorialHandler(long n)
    {
        if (n < 0)
            return 0;
        long w = 0;
        boolean success = true;
        try {
            w = Maths.LongFactorial(n);
        }
        catch (OverflowException e) {
            System.out.println(e);
            success = false;
        }
        if (success)
                return w;
        return 0;
    }

    public static double[][] IntToDoubleArray(int[][] m)
    {
        double[][] l = new double[m.length][];
        for (int i = 0; i < m.length; ++i)
        {
            double[] f = new double[m[i].length];
            for (int k = 0; k < m[i].length; ++k)
            {
                f[k] = (double) m[i][k];
            }
            l[i] = f;
        }
        return l;
    }

    public static int[][] DoubleToIntArray(double[][] m)
    {
        int[][] l = new int[m.length][];
        for (int i = 0; i < m.length; ++i)
        {
            int[] f = new int[m[i].length];
            for (int k = 0; k < m[i].length; ++k)
            {
                f[k] = (int) Math.round(m[i][k]);
            }
            l[i] = f;
        }
        return l;
    }
}