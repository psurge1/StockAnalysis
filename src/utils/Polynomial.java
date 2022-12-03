package utils;

public class Polynomial {
    private int npoints = -1;
    private double[] xs = null;
    private double[] ys = null;
    private double[] coefficients = null;
    private double[][] rrefmatrix = null;
    private String eq = "";
    public Polynomial()
    {

    }

    public Polynomial(int npoints)
    {
        this.npoints = npoints;
    }

    public double[] getCoefficients()
    {
        return this.coefficients;
    }

    public double[][] getrrefmatrix()
    {
        return this.rrefmatrix;
    }

    public void setnpoints(int npoints) throws OutOfRangeException
    {
        if (npoints > 0)
        {
            this.npoints = npoints;
            this.xs = null;
            this.ys = null;   
        }
        else
            throw new OutOfRangeException("Non positive argument provided");
    }

    public int getnpoints()
    {
        return this.npoints;
    }

    public double[] getX()
    {
        return xs;
    }

    public boolean setX(double ... args)
    {
        if (args.length != this.npoints)
            return false;
        xs = args;
        return true;
    }

    public double[] getY()
    {
        return ys;
    }

    public boolean setY(double ... args)
    {
        if (args.length != this.npoints)
            return false;
        ys = args;
        return true;
    }

    public boolean init()
    {
        if (this.xs.length != this.npoints || this.ys.length != this.npoints)
            return false;
        // coefficients = new double[];
        double[][] eq = new double[this.npoints][];
        this.coefficients = new double[this.npoints];
        for (int i = 0; i < this.npoints; ++i)
        {
            double[] w = new double[this.npoints + 1];
            for (int k = 0; k < this.npoints; ++k)
            {
                w[k] = Math.pow(this.xs[i], this.npoints - k - 1);
            }
            w[this.npoints] = this.ys[i];
            eq[i] = w;
        }
        this.rrefmatrix = RREF.rref(eq);
        for (int a = 0; a < this.npoints; ++a)
            this.coefficients[a] = this.rrefmatrix[a][this.rrefmatrix[a].length - 1];
        return true;
    }

    public void genEq()
    {
        this.eq = "";
        for (int i = 0, n = this.getCoefficients().length; i < n; ++i)
        {
            this.eq += String.format("%f", this.getCoefficients()[i]) + "x^" + (n - i - 1);
            if (i != n - 1)
                this.eq += " + ";
        }
    }

    public String getEq()
    {
        return this.eq;
    }

    public double evaluate(double x)
    {
        double r = 0;
        for (int i = 0, n = this.getCoefficients().length; i < n; ++i)
        {
            r += this.getCoefficients()[i] * Math.pow(x, n - i - 1);
        }
        return r;
    }
}