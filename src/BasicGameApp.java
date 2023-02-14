import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.event.*;

public class BasicGameApp implements Runnable, KeyListener, MouseListener {

    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;

    public Image jaredPic;
    public Image binPic;
    public Image backgroundPic;

    public Player jared;
    public Obstacle bin;


    //Mouse position variables
    public int mouseX, mouseY;

    //button
    public Button button1;


    //timer variables
    public long startTime;
    public long currentTime;
    public long elapsedTime;

    public boolean startTimer;

    public static void main(String[] args) {
        BasicGameApp myGame = new BasicGameApp();
        new Thread(myGame).start();
    }

    public BasicGameApp() {
        setUpGraphics();
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);

        jaredPic = Toolkit.getDefaultToolkit().getImage("white duck.png");
        jared = new Player ("Jared", 700, 350, 300, 315, 0, 0);

        binPic = Toolkit.getDefaultToolkit().getImage("recycle.png");
        bin = new Obstacle ("Recycle", 1, 1, 200, 300);


        backgroundPic = Toolkit.getDefaultToolkit().getImage("background.gif");

    }

    public void run() {
        while (true) {
            moveThings();
            checkIntersections();
            render();
            pause(20);
        }
    }

    public void moveThings() {

    }

    public void checkIntersections() {

    }


    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }


    public void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        JPanel panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.addMouseListener(this);
        canvas.addKeyListener(this);
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.


        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE");


    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //draw characters to the screen

        g.drawImage(backgroundPic, 0,0, WIDTH, HEIGHT, null);
        g.drawImage(jaredPic, jared.xpos, jared.ypos, jared.width, jared.height, null);
        g.drawImage(binPic, bin.xpos, bin.ypos, bin.width, bin.height, null);

        g.dispose();
        bufferStrategy.show();
    }

}

    //*******************************************************************************




