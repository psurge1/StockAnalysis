import javax.swing.JPanel;


public class Element extends JPanel
{
    private int w, h;
    public Element()
    {
        w = 0;
        h = 0;
    }

    public Element(int w, int h)
    {
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

    public void setXY(int w, int h)
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