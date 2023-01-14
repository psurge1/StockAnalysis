

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.HashMap;


public class Gui
{
    JPanel panel;
    Chart chart;
    JFrame frame;
    HashMap<String, Number> values;
    
    public Gui()
    {
        values = new HashMap<>();
        values.put("Tech", 1);
        values.put("Oil and Gas", 2);
        values.put("Pharmacy", 1.5);

        chart = Chart.chartFromKwargs("../storage/diversitypie.png", values);
        // chart.setXY(0, 0);
        // chart.setSize(10, 10);
        
        panel = new JPanel();

        frame = new JFrame();
        frame.setSize(1500, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.add(panel);
        frame.add(chart);
    }

    public static void main(String[] args) {
        new Gui();
    }
}