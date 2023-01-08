import utils.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.*;

public class Main
{
    int width, height;
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    Main()
    {
        // generateDimensions();
        // establish();
        // Chart c = new Chart();
    }

    Main(int w, int h)
    {
        establish(w, h);
    }

    /**
     * 
     * generateDimensions uses the screen width and height to produce proportional width and heigh parameters for the GUI
     */
    private void generateDimensions()
    {
        // Dimension sc = Toolkit.getDefaultToolkit().getScreenSize();
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        width = gd.getDisplayMode().getWidth();
        height = gd.getDisplayMode().getHeight();
    }

    private void establish()
    {
        establish(width / 2, height / 2);
    }

    
    /**
     * 
     * establish establishes a GUI window with width w and height h
     * @param w pixel width
     * @param h pixel height
     */
    private void establish(int w, int h)
    {
        System.out.printf("%d %d", w, h);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, h, w));
        panel.setLayout(new GridLayout());

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("");
        frame.pack();
        frame.setVisible(true);
    }







    public static void main(String[] args) throws OverflowException, OutOfRangeException, IOException
    {
        // BELOW CODE WORKS AND MUST BE KEPT
        // EXECUTE BASH COMMAND TO FILL news.csv WITH NEWS ARTICLES
        // Runtime.getRuntime().exec(String.format("py Python/retrieve.py %s", "news"));
        // THIS IS BETTER
        // CmdExec.exec("py Python/retrieve.py %s", "news");


        // String s = MFile.fromPath("../data/news.csv");
        // System.out.println(s);

        // GET NEWS ITEMS FROM news.csv AND STORE IN NewsItem ARRAY
        // NewsItem[] newsItems = NewsItem.newsItemsFromFile("../data/news.csv");
        // for (NewsItem item : newsItems)
        // {
        //     System.out.println(item);
        // }
        
        // GET CHART FROM PYTHON
        // HashMap<String, Number> hoip = new HashMap<>();
        // hoip.put("FROG", 1.5);
        // hoip.put("HORSE", 3.0);
        // hoip.put("DRAGONIAN BEAST", 4.5);
        // hoip.put("LEOPARD FLYER", 10.0);
        // Chart c = Chart.chartFromKwargs(hoip);

        // EXAMPLE CODE TO PRINT OUTPUT OF COMMAND TO CONSOLE
        // Process p = CmdExec.exec("py Python/retrieve.py %s", "news");
        // BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        // String ret = in.readLine();
        // System.out.println("D: " + ret);

        // new Main(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
        // new Main();

        // Stock c = new Stock("AAPL", 0, "Apple Inc.", "Tech");
        

        // String fmt = "0x10x0.1";
        // String fmt = "2000x2040x0.1";

        // String fmt = args[0]; // format
        // int npoints = Integer.valueOf(args[1]); // npoints
        // double[] xVs = stodArr(args[3].split(" ")); // xs
        // double[] yVs = stodArr(args[4].split(" ")); // ys
        // PrintWriter out = new PrintWriter("../storage/points.txt");

        // Polynomial p = new Polynomial();
        // p.setnpoints(npoints);
        // p.setX(xVs);
        // p.setY(yVs);
        // p.init();

        // double[] format = extract(fmt, "x");
        // for (double i = format[0]; i <= format[1]; i += format[2])
        //     out.write(i + " ");
        // out.write("\n");
        // for (double i = format[0]; i <= format[1]; i += format[2])
        //     out.write(p.evaluate(i) + " ");
        // out.write("\n");
        // p.genEq();
        // out.write(p.getEq());
        
        // out.close();

        // // Testing
        // System.out.println("Testing");
        // double[] xV = p.getX();
        // double[] yV = p.getY();
        // double vd;
        // for (int i = 0; i < p.getnpoints(); ++i)
        // {
        //     vd = p.evaluate(xV[i]);
        //     System.out.printf("--- (%.0f) %f == %f %b\n", xV[i], vd, yV[i], vd == yV[i]); // ERROR SHOULD BE TRUE
        // }
    }





    /**
     * 
     * @param s
     * @param sep
     * @return double array with all values separated by sep
     */
    public static double[] extract(String s, String sep)
    {
        int r1 = s.indexOf(sep);
        int r2 = s.indexOf(sep, r1 + 1);
        double[] k = {
            Double.valueOf(s.substring(0, r1)),
            Double.valueOf(s.substring(r1 + 1, r2)),
            Double.valueOf(s.substring(r2 + 1, s.length()))
        };
        return k;
    }

    /**
     * 
     * @param arr String array
     * @return double array
     */
    public static double[] stodArr(String[] arr)
    {
        arrprint(arr);
        int n = arr.length;
        double[] dArr = new double[n];
        for (int i = 0; i < n; ++i)
        {
            dArr[i] = Double.valueOf(arr[i]);
        }
        arrprint(dArr);
        return dArr;
    }

    /**
     * 
     * prints array
     * @param arr String array
     */
    public static void arrprint(String[] arr)
    {
        for (String c: arr)
        {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    /**
     * 
     * prints array
     * @param arr double array
     */
    public static void arrprint(double[] arr)
    {
        for (double c: arr)
        {
            System.out.print(c + " ");
        }
        System.out.println();
    }
}