import java.awt.*;


public class       Score extends Rectangle {


    static int GAME_WIDTH;
    static int GAME_HEIGHT;

    int player1;
    int player2;
    int winScore = 5;
    PlayerPanel panel;          //Player Panel
    ComputerPanel cPanel;           //Computer Panel
    Ball ball;

    Score(int GAME_WIDTH, int GAME_HEIGHT) {

        Score.GAME_WIDTH = GAME_WIDTH;
        Score.GAME_HEIGHT = GAME_HEIGHT;

    }

    public void draw(Graphics g) {

        g.setColor(Color.white);

        g.setFont(new Font("Consolas", Font.PLAIN, 60));


        g.drawLine(GAME_WIDTH / 2, 0, GAME_WIDTH / 2, GAME_HEIGHT);


        g.drawString(player1 / 10 + String.valueOf(player1 % 10), (GAME_WIDTH / 2) - 85, 50);
        g.drawString(player2 / 10 + String.valueOf(player2 % 10), (GAME_WIDTH / 2) + 20, 50);
        //display the name of players
        g.drawString (Menu.p1_name.getText(), 100,100);
        g.drawString (Menu.p2_name.getText(), 600,100);
    }

    public void checkScore() {
        try {
            if (player1 == winScore || player2 == winScore) {

                ball.xVelocity = 0;
                ball.yVelocity = 0;

            }
        } catch(Exception e) {
           panel.run = false;
           cPanel.run = false;
        }
    }


}