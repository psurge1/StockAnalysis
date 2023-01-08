

public class ElementD extends Element
{
    private int w, h;
    public ElementD()
    {
        super();
        w = 0;
        h = 0;
    }

    public ElementD(int x, int y)
    {
        super(x, y);
        w = 0;
        h = 0;
    }

    public ElementD(int x, int y, int w, int h)
    {
        super(x, y);
        this.w = w;
        this.h = h;
    }

    public int getW()
    {
        return w;
    }

    public int getH()
    {
        return h;
    }

    public void setWH(int w, int h)
    {
        this.w = w;
        this.h = h;
    }

    public void setW(int w)
    {
        this.w = w;
    }

    public void setH(int h)
    {
        this.h = h;
    }
}