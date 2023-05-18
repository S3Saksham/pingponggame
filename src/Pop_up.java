import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Pop_up extends JComponent implements ActionListener{


	JFrame GameOverFrame;
	 JPanel GameOverPanel;
	 JPanel lbl_win_Panel;
	 static JLabel lbl_win;
static PlayerFrame frame;
static	ComputerFrame cframe;

	 JButton btn_TryAgain, btn_Menu, btn_quit;
		
	Font titleFont = new Font("Times New Roman",Font.PLAIN,80);
	Font normalFont = new Font("Times New Roman",Font.PLAIN,20);
		
		 Pop_up() {
			 GameOverFrame = new JFrame("Game Over");
			 GameOverFrame.setSize(800, 600);
			 GameOverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			 GameOverFrame.setBackground(Color.BLACK);
			 GameOverFrame.setLayout(null);
			 GameOverFrame.setResizable(false);

			 GameOverPanel = new JPanel();
			 GameOverPanel.setLayout(null);
			 GameOverPanel.setBounds(0, 0, 800, 600);
			 GameOverPanel.setBackground(Color.black);

			 lbl_win_Panel = new JPanel();
			 lbl_win_Panel.setBounds(100, 100, 600, 100);
			 lbl_win_Panel.setBackground(Color.black);
			    
			  

			   
			    lbl_win.setForeground(Color.white);
			    lbl_win.setFont(titleFont);
			    lbl_win_Panel.add(lbl_win);


			    btn_TryAgain = new JButton("Play Again");
			    btn_TryAgain.setBounds(300 , 280 , 200, 40);
			    btn_TryAgain.setBackground(Color.black);
			    btn_TryAgain.setForeground(Color.white);
			    btn_TryAgain.addActionListener(this);
			    btn_TryAgain.setFont(normalFont);

			    btn_Menu = new JButton("Go to Menu");
			    btn_Menu.setBounds(300 , 340 , 200 , 40);
			    btn_Menu.setBackground(Color.black);
			    btn_Menu.setForeground(Color.white);
			    btn_Menu.addActionListener(this);
			    btn_Menu.setFont(normalFont);
			    
			    btn_quit = new JButton("Quit");
			    btn_quit.setBounds(300 , 400 , 200 , 40);
			    btn_quit.setBackground(Color.black);
			    btn_quit.setForeground(Color.white);
			    btn_quit.addActionListener(this);
			    btn_quit.setFont(normalFont);

			    GameOverFrame.add(GameOverPanel);
			    GameOverPanel.add(lbl_win_Panel);
			    GameOverPanel.add(btn_TryAgain);
			    GameOverPanel.add(btn_Menu);
			    GameOverPanel.add(btn_quit);

			    GameOverFrame.setVisible(true);

			    GameOverFrame.setResizable(false);

			    GameOverFrame.setLocationRelativeTo(null);

			    GameOverFrame.setVisible(true);
		 }


		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.getSource() == btn_TryAgain)
			{
				
				GameOverFrame.setVisible(false);
				GameOverFrame.dispose();



				if (Menu.choice == 0) {

					Menu.player_Frame.setVisible(false);
					Menu.player_Frame.dispose();


					try {
						frame.setVisible(false);
						frame.dispose();
					}catch (Exception ignored){

					}

					frame = new PlayerFrame();



				}
				else if (Menu.choice == 1) 
				{


					Menu.computer_Frame.setVisible(false);
					Menu.computer_Frame.dispose();


					try {
						cframe.setVisible(false);
						cframe.dispose();
					}catch (Exception ignored){

					}

					cframe = new ComputerFrame();


				}
			}
			else if(arg0.getSource() == btn_Menu) {
				GameOverFrame.setVisible(false);
				GameOverFrame.dispose();


				if (Menu.choice == 0) {

					Menu.player_Frame.setVisible(false);
					Menu.player_Frame.dispose();


					try {
						frame.setVisible(false);
						frame.dispose();
					}catch (Exception ignored){

					}
			}
				else if (Menu.choice == 1)
				{

					Menu.computer_Frame.setVisible(false);
					Menu.computer_Frame.dispose();


					try {
						cframe.setVisible(false);
						cframe.dispose();
					}catch (Exception ignored){

					}

				}
			new Menu();

			}
			else if(arg0.getSource() == btn_quit) {
				GameOverFrame.setVisible(false);
				GameOverFrame.dispose();
				System.exit(ABORT);
			}
			
		}
		
		 }