import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import static java.lang.StrictMath.abs;

public class ComputerPanel extends JPanel implements Runnable{

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    String endLine = "============== Game Ended =============== \n";


    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;
    boolean run = true;

    ComputerPanel(){

        newPaddles();
        newBall();
        score = new Score(GAME_WIDTH,GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);


        gameThread = new Thread(this);
        gameThread.start();
    }



    public void newBall() {

        random = new Random();
        ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);

    }

    public void newPaddles() {

        paddle1 = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
        paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);

    }

    public void paint(Graphics g) {

        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);

    }

    public void draw(Graphics g) {

        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
        Toolkit.getDefaultToolkit().sync();


    }

    public void move() {

        paddle1.move();
        paddle2.move();
        ball.move();

    }

    public void checkCollision() {

        //bounce ball off top & bottom window edges

        if(ball.y <=0) {
            ball.setYDirection(-ball.yVelocity);

        }

        if(ball.y >= GAME_HEIGHT-BALL_DIAMETER) {
            ball.setYDirection(-ball.yVelocity);

        }

        //bounce ball off paddles

        if(ball.intersects(paddle1)) {
            ball.IsMovingRight = true;
            ball.IsMovingLeft = false;

            Toolkit.getDefaultToolkit().beep();
            paddle1.setYDirection(0);
            ball.xVelocity = Math.abs(ball.xVelocity);

            ball.xVelocity++; //optional for more difficulty

            if(ball.yVelocity>0)
                ball.yVelocity++; //optional for more difficulty

            else
                ball.yVelocity--;

            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);

        }

        if(ball.intersects(paddle2)) {
            ball.IsMovingRight = false;
            ball.IsMovingLeft = true;

            Toolkit.getDefaultToolkit().beep();

            paddle2.setYDirection(0);
            ball.xVelocity = Math.abs(ball.xVelocity);

            ball.xVelocity++; //optional for more difficulty

            if(ball.yVelocity>0)
                ball.yVelocity++; //optional for more difficulty

            else

                ball.yVelocity--;

            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);

        }

        //stops paddles at window edges

        if(paddle1.y<=0)
            paddle1.y=0;

        if(paddle1.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
            paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;

        if(paddle2.y<=0)
            paddle2.y=0;

        if(paddle2.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
            paddle2.y = GAME_HEIGHT-PADDLE_HEIGHT;

        //give a player 1 point and creates new paddles & ball

        if(ball.x <=0) {

            score.player2++;
            newPaddles();
            newBall();
            System.out.println("Computer: "+score.player2);
            try {
                FileWriter myWriter = new FileWriter("gameLogFile.txt",true);
                myWriter.write("Computer: "+score.player2 +" \n");

                myWriter.close();
            } catch (IOException ea) {
                System.out.println("An error occurred.");
                ea.printStackTrace();
            }


        }

        if(ball.x >= GAME_WIDTH-BALL_DIAMETER) {

            score.player1++;
            newPaddles();
            newBall();
            System.out.println(Menu.p1_name.getText()+" : "+score.player1);
            try {
                FileWriter myWriter = new FileWriter("gameLogFile.txt",true);
                myWriter.write(Menu.p1_name.getText()+" : "+score.player1);

                myWriter.close();
            } catch (IOException ea) {
                System.out.println("An error occurred.");
                ea.printStackTrace();
            }

        }


    }
    public void AI() {
        if (ball.IsMovingRight) {

            if (paddle2.y <= ball.y && abs(paddle2.y - ball.y) >= 12) {
                paddle2.setYDirection(ball.yVelocity);
            } else if (paddle2.y >= ball.y && abs(paddle2.y - ball.y) >= 12) {
                paddle2.setYDirection(-(ball.yVelocity));
            }

        }

    }
    public void run() {

        //game loop
        long start = System.currentTimeMillis();
        long lastTime = System.nanoTime();
        double amountOfTicks =60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while(run) {


            long now = System.nanoTime();
            delta += (now -lastTime)/ns;
            lastTime = now;
            if(delta >=1) {

                try{
                    score.checkScore();

                }catch (Exception e){
                    long finish = System.currentTimeMillis();
                    long timeElapsed = finish - start;
                    System.out.println("GAME OVER !!" );
                    System.out.println("Time taken: " + timeElapsed+" ms");
                    try {
                        FileWriter myWriter = new FileWriter("gameLogFile.txt",true);
                        myWriter.write("GAME OVER !! \n");
                        myWriter.write("Time taken: " + timeElapsed+" ms \n");

                        myWriter.close();
                    } catch (IOException ea) {
                        System.out.println("An error occurred.");
                        ea.printStackTrace();
                    }



                    if(score.player1 > score.player2){
                        Pop_up.lbl_win = new JLabel(Menu.p1_name.getText()+" Won");
                        System.out.println(Menu.p1_name.getText()+" is the WINNER");
                        System.out.println(endLine);

                        try {
                            FileWriter myWriter = new FileWriter("gameLogFile.txt",true);
                            myWriter.write(Menu.p1_name.getText()+" is the WINNER \n");
                            myWriter.write(endLine);

                            myWriter.close();
                        } catch (IOException ea) {
                            System.out.println("An error occurred.");
                            ea.printStackTrace();
                        }

                    }else{
                        Pop_up.lbl_win = new JLabel("Computer Won");
                        System.out.println("Computer is the WINNER");
                        System.out.println(endLine);

                        try {
                            FileWriter myWriter = new FileWriter("gameLogFile.txt",true);
                            myWriter.write("Computer is the WINNER \n");
                            myWriter.write(endLine);

                            myWriter.close();
                        } catch (IOException ea) {
                            System.out.println("An error occurred.");
                            ea.printStackTrace();
                        }

                    }

                    new Pop_up();
                    break;
                }

                AI();
                checkCollision();
                move();
                repaint();
                delta--;

            }

        }

    }

    public class AL extends KeyAdapter{

        public void keyPressed(KeyEvent e) {

            paddle1.keyPressed(e);


        }

        public void keyReleased(KeyEvent e) {

            paddle1.keyReleased(e);


        }

    }

}