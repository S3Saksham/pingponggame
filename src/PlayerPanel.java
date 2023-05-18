import javax.swing.*;
import java.awt.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class PlayerPanel extends JPanel implements Runnable{

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    boolean run = true;
    String endLine = "============== Game Ended =============== \n";


    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;


    
    PlayerPanel(){

        newPaddles();
        newBall();
        score = new Score(GAME_WIDTH,GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        try {
            File myObj = new File("gameLogFile.txt");

            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


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

            ball.xVelocity++;                   //optional for more difficulty

            if(ball.yVelocity>0)
                ball.yVelocity++;                //optional for more difficulty

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

            ball.xVelocity++;                   //optional for more difficulty

            if(ball.yVelocity>0)
                ball.yVelocity++;               //optional for more difficulty

            else

                ball.yVelocity--;

            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);

        }

                                                //stops paddles at window edges left and right

        if(paddle1.y<=0)
            paddle1.y=0;

        if(paddle1.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
            paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;

        if(paddle2.y<=0)
            paddle2.y=0;

        if(paddle2.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
            paddle2.y = GAME_HEIGHT-PADDLE_HEIGHT;

                                              //give a player  point and creates new paddles & ball

        if(ball.x <=0) {                    // if ball passes beyond left

            score.player2++;
            newPaddles();
            newBall();
            System.out.println(Menu.p2_name.getText()+" : "+score.player2);

            try {
                FileWriter myWriter = new FileWriter("gameLogFile.txt",true);
                myWriter.write(Menu.p2_name.getText()+" : "+score.player2+"\n");
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }


        }

        if(ball.x >= GAME_WIDTH-BALL_DIAMETER) {            // if ball passes beyond right

            score.player1++;
            newPaddles();
            newBall();
            System.out.println(Menu.p1_name.getText()+" : "+score.player1);

            try {
                FileWriter myWriter = new FileWriter("gameLogFile.txt",true);
                myWriter.write(Menu.p1_name.getText()+" : "+score.player1+"\n");
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
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
                    System.out.println("GAME OVER !!" );
                    try {
                        FileWriter myWriter = new FileWriter("gameLogFile.txt",true);
                        myWriter.write("GAME OVER !! \n");
                        myWriter.close();
                    } catch (IOException ea) {
                        System.out.println("An error occurred.");
                        ea.printStackTrace();
                    }

                    long timeElapsed = finish - start;
                    System.out.println("Time taken: " + timeElapsed+" ms");
                    try {
                        FileWriter myWriter = new FileWriter("gameLogFile.txt",true);
                        myWriter.write("Time taken: " + timeElapsed +" ms \n");
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
                        new Pop_up();
                    }else{
        			    Pop_up.lbl_win = new JLabel(Menu.p2_name.getText()+" Won");
                        System.out.println(Menu.p2_name.getText()+" is the WINNER");
                        System.out.println(endLine);

                        try {
                            FileWriter myWriter = new FileWriter("gameLogFile.txt",true);
                            myWriter.write(Menu.p2_name.getText()+" is the WINNER \n");
                            myWriter.write(endLine);
                            myWriter.close();
                        } catch (IOException ea) {
                            System.out.println("An error occurred.");
                            ea.printStackTrace();
                        }
                        new Pop_up();
                    }
                    break;
                }
                
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
            paddle2.keyPressed(e);

        }

        public void keyReleased(KeyEvent e) {

            paddle1.keyReleased(e);
            paddle2.keyReleased(e);

        }

    }

    

}