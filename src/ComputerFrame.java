import javax.swing.*;
import java.awt.*;

public class ComputerFrame extends JFrame{
    ComputerPanel panel;

    ComputerFrame(){

        panel = new ComputerPanel();

        this.add(panel);

        this.setTitle("Pong Game PvC");     //GameFrame.setTitle

        this.setBackground(Color.black);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.pack();

        this.setVisible(true);

        this.setResizable(false);

        this.setLocationRelativeTo(null);

    }
}
