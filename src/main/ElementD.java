public class ElementD extends Element
{
    private int x, y;
    public ElementD()
    {
        super();
        setXY(0, 0);
    }

    public ElementD(int w, int h)
    {
        super(w, h);
        setXY(0, 0);
    }

    public ElementD(int w, int h, int x, int y)
    {
        super(w, h);
        setXY(x, y);
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setXY(int x, int y)
    {
        this.x = x;
        this.y = y;
        
        // for certain JComponents
        setLocation(x, y);
    }

    public void setX(int x)
    {
        setXY(x, getY());
    }

    public void setY(int y)
    {
        setXY(getX(), y);
    }
}