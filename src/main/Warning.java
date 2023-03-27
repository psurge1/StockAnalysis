import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;



public class Warning extends ElementD {
    JFrame frame;
    JPanel panel;
    JLabel label;
    public Warning(String message)
    {
        setWH(400, 100);
        frame = new JFrame("Warning!");
        frame.setSize(getW(), getH());

        panel = new JPanel();
        frame.add(panel);

        label = new JLabel(message);
        panel.add(label);

        frame.setVisible(true);
    }
}
