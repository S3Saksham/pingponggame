import javax.swing.*;
import java.awt.*;

public class PlayerFrame extends JFrame {

    static PlayerPanel panel;

    PlayerFrame(){

        panel = new PlayerPanel();

        this.add(panel);
        
        this.setTitle("Pong Game PvP");     //GameFrame.setTitle


        this.setBackground(Color.black);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.pack();

        this.setVisible(true);

        this.setResizable(false);

        this.setLocationRelativeTo(null);



    }

}
