import java.util.HashMap;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import utils.CmdExec;



public class Chart extends ElementD
{
    private String path_to_img;
    private BufferedImage chartImage;

    public Chart(String path)
    {
        super();
        path_to_img = path;
        try
        {
            chartImage = ImageIO.read(new File(path_to_img));
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }

    public Chart(String path, int w, int h)
    {
        this.setWH(w, h);

        path_to_img = path;
        try
        {
            chartImage = ImageIO.read(new File(path_to_img));
        }
        catch (IOException e)
        {

        }
    }
    

    public Chart(String path, int w, int h, int x, int y)
    {
        super(x, y, w, h);
        path_to_img = path;
        try
        {
            chartImage = ImageIO.read(new File(path_to_img));
        }
        catch (IOException e)
        {

        }
    }
    

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(chartImage, getX(), getY(), this);
    }

    public static Chart chartFromKwargs(HashMap<String, Number> hp)
    {
        return chartFromKwargs("../storage/diversitypie.png", hp);
    }

    public static Chart chartFromKwargs(String path, HashMap<String, Number> hp)
    {
        Object[] args = new Object[hp.size()];
        String[] keySet = hp.keySet().toArray(new String[0]);

        String cmd = "py Python/plot.py \"" + path + "\"";
        for (int i = 0, n = hp.size(); i < n; ++i)
        {
            cmd += " %s";
            args[i] = "\"" + keySet[i] + "\"=" + hp.get(keySet[i]) + " ";
        }
        
        // EXAMPLE ARGS
        // args = {"\"FROG\"=1.5", "\"HORSE\"=3.0", "\"DRAGONIAN BEAST\"=4.5", "\"LEOPARD FLYER\"=10"};
        
        CmdExec.exec(cmd, args);
        
        // TESTING
        // System.out.println(cmd);
        // for (Object o : args)
        // {
        //     System.out.print(o + " ");
        // }
        // System.out.println();
        
        // EXAMPLE BASH COMMAND
        // py Python/plot.py "../storage/diversitypie.png" FROG=1.5 HORSE=3.0 "DRAGONIAN BEAST"=4.5 "LEOPARD FLYER"=10

        return new Chart(path);
    }
}

/*
JAVA IMPLEMENTATION (without Python)

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JFrame;

class Slice {
   double value;
   Color color;
   public Slice(double value, Color color) {  
      this.value = value;
      this.color = color;
   }
}

class Chart extends JComponent {
    Slice[] slices = {
        new Slice(5, Color.black), 
        new Slice(33, Color.green),
        new Slice(20, Color.yellow),
        new Slice(15, Color.red)
    };
    
    Chart() {}
    
    public void paint(Graphics g) {
        drawPie((Graphics2D) g, getBounds(), slices);
    }
    
    void drawPie(Graphics2D g, Rectangle area, Slice[] slices) {
        double total = 0.0D;
        for (int i = 0; i < slices.length; i++) {
            total += slices[i].value;
        }
        double curValue = 0.0D;
        int startAngle = 0;
        for (int i = 0; i < slices.length; i++) {
            startAngle = (int) (curValue * 360 / total);
            int arcAngle = (int) (slices[i].value * 360 / total);
            g.setColor(slices[i].color);
            g.fillArc(area.x, area.y, area.width, area.height, startAngle, arcAngle);
            curValue += slices[i].value;
        }
    }
}
*/