

public class ElementD extends Element
{
    private int x, y;
    public ElementD()
    {
        super();
        x = 0;
        y = 0;
    }

    public ElementD(int w, int h)
    {
        super(w, h);
        x = 0;
        y = 0;
    }

    public ElementD(int w, int h, int x, int y)
    {
        super(w, h);
        this.x = x;
        this.y = y;
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
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }
}