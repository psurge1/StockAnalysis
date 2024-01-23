import java.util.HashMap;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;



public class Chart extends ElementD
{
    private String path_to_img;
    private BufferedImage chartImage;
    private JLabel imageLabel;

    // CONSTRUCTORS
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
        super(w, h);

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
        super(w, h, x, y);

        path_to_img = path;
        try
        {
            chartImage = ImageIO.read(new File(path_to_img));
        }
        catch (IOException e)
        {

        }
    }
    
    public void setLabel()
    {
        imageLabel = new JLabel(new ImageIcon(chartImage));
    }

    public JLabel getJLabel()
    {
        return imageLabel;
    }

    public BufferedImage getImage()
    {
        return chartImage;
    }
    
    // FACTORY METHODS
    public static Chart chartFromKwargs(HashMap<String, Number> hp)
    {
        return chartFromKwargs(FilePaths.STOCKGRAPH.value, 2, 2, hp);
    }

    public static Chart chartFromKwargs(double w, double h, HashMap<String, Number> hp)
    {
        return chartFromKwargs(FilePaths.STOCKGRAPH.value, w, h, hp);
    }

    public static Chart chartFromKwargs(String path, HashMap<String, Number> hp)
    {
        return chartFromKwargs(path, 2, 2, hp);
    }

    public static Chart chartFromKwargs(String path, double w, double h, HashMap<String, Number> hp)
    {
        Object[] args = new Object[hp.size()];
        String[] keySet = hp.keySet().toArray(new String[0]);
        String cmd = "py " + FilePaths.PYTHON.value + "plot.py \"" + path + "\" \"" + w + "\" \"" + h + "\"";
        for (int i = 0, n = hp.size(); i < n; ++i)
        {
            cmd += " %s";
            args[i] = "\"" + keySet[i] + "\"=" + hp.get(keySet[i]) + " ";
        }
        
        // EXAMPLE ARGS
        // args = {"\"FROG\"=1.5", "\"HORSE\"=3.0", "\"COW\"=4.5", "\"LEOPARD\"=10"};
        
        // EXAMPLE BASH COMMAND
        // py Python/plot.py "../storage/diversitypie.png" FROG=1.5 HORSE=3.0 "COW"=4.5 "LEOPARD"=10

        CmdExec.exec(cmd, args);

        return new Chart(path);
    }
}
