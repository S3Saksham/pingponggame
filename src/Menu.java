import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Menu extends JComponent implements ActionListener {
    JFrame mainFrame;
    JPanel mainPanel;
    JPanel lbl_Panel;
    JButton btn_PvP, btn_PvC;
    JLabel lbl_PP;
    
    static JTextField p1_name;
    static JTextField p2_name;

    JPanel inputPanel;
    int player_inputs;

    static PlayerFrame player_Frame;
    static ComputerFrame computer_Frame;

    static int choice; //player vs player = 0    Player vs comp  = 1
    
    Font titleFont = new Font("Times New Roman",Font.PLAIN,80);
    Font normalFont = new Font("Times New Roman",Font.PLAIN,20);

public Menu(){

    mainFrame = new JFrame("Ping Pong");
    mainFrame.setSize(800, 600);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setBackground(Color.BLACK);
    mainFrame.setLayout(null);
    mainFrame.setResizable(false);

    mainPanel = new JPanel();
    mainPanel.setLayout(null);
    mainPanel.setBounds(0, 0, 800, 600);
    mainPanel.setBackground(Color.black);

    lbl_Panel = new JPanel();
    lbl_Panel.setBounds(100, 100, 600, 150);
    lbl_Panel.setBackground(Color.black);


    lbl_PP = new JLabel("Ping Pong");

    lbl_PP.setForeground(Color.white);
    lbl_PP.setFont(titleFont);
    lbl_Panel.add(lbl_PP);


    btn_PvP = new JButton("Player vs Player");
    btn_PvP.setBounds(300 , 310 , 200, 40);
    btn_PvP.setBackground(Color.black);
    btn_PvP.setForeground(Color.white);
    btn_PvP.addActionListener(this);
    btn_PvP.setFont(normalFont);

    btn_PvC = new JButton("Player vs Computer");
    btn_PvC.setBounds(300 , 400 , 200 , 40);
    btn_PvC.setBackground(Color.black);
    btn_PvC.setForeground(Color.white);
    btn_PvC.addActionListener(this);
    btn_PvC.setFont(normalFont);

    mainFrame.add(mainPanel);
    mainPanel.add(lbl_Panel);
    mainPanel.add(btn_PvP);
    mainPanel.add(btn_PvC);

    mainFrame.setResizable(false);

    mainFrame.setLocationRelativeTo(null);

    mainFrame.setVisible(true);
    
    // input names 
    p1_name = new JTextField(10);

    p1_name.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
            if (p1_name.getText().length() >= 8 )        // limit text Field to 8 characters
                e.consume();
        }
    });
    p2_name = new JTextField(10);
    p2_name.addKeyListener(new KeyAdapter() {
        public void keyTyped(KeyEvent e) {
            if (p2_name.getText().length() >= 8 )   // limit text Field to 8 characters
                e.consume();
        }
    });

    inputPanel = new JPanel();
    inputPanel.add(new JLabel("Player 1 Name"));
    
    inputPanel.add(p1_name);
    inputPanel.add(Box.createHorizontalStrut(10)); // a spacer


}
    JOptionPane pane = new JOptionPane("Message");


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btn_PvP) {
        	choice = 0;
            inputPanel.add(new JLabel("Player 2 Name"));
            inputPanel.add(p2_name);

            player_inputs = pane.showConfirmDialog(null, inputPanel,
                    "Enter Your names ", pane.DEFAULT_OPTION);
            if (player_inputs == pane.CLOSED_OPTION){
                pane.setVisible(false);

            }


            player_Frame =  new PlayerFrame();

            mainFrame.setVisible(false);
        }
        else if(e.getSource() == btn_PvC) {
        	choice = 1;
            player_inputs = JOptionPane.showConfirmDialog(null, inputPanel, 
                    "Enter Your name ", JOptionPane.DEFAULT_OPTION);
            if (player_inputs == JOptionPane.CLOSED_OPTION){

            }

            computer_Frame = new ComputerFrame();

            mainFrame.setVisible(false);
        }
    }

}
