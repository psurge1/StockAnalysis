import javax.swing.JComponent;



public class Element extends JComponent
{
    private int w, h;
    public Element()
    {
        setWH(0, 0);
    }

    public Element(int w, int h)
    {
        setWH(w, h);
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

        // for certain JComponents
        this.setSize(w, h);
    }
    
    public void setW(int w)
    {
        setWH(w, getH());
    }

    public void setH(int h)
    {
        setWH(getW(), h);
    }
}