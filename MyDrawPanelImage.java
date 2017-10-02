package HFJ;

import java.awt.*;
import javax.swing.*;

public class MyDrawPanelImage extends JPanel {
    public void paintComponent(Graphics g) {
        Image image = new ImageIcon("C:/7001.jpg").getImage();
        g.drawImage(image, 3, 4, this);
    }
}
