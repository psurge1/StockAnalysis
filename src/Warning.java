import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class Warning {
    JFrame frame;
    JPanel panel;
    JLabel label;
    public Warning(String message)
    {
        frame = new JFrame("Warning!");
        frame.setSize(400, 100);

        panel = new JPanel();
        frame.add(panel);

        label = new JLabel(message);
        panel.add(label);

        frame.setVisible(true);
    }
}
