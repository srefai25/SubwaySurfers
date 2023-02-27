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

    public boolean gameStart = false;

    public Image jaredPic;
    public Image obstaclePic;
    public Image flyjaredPic;
    public Image fluffPic;
    public Image backgroundPic;
    public Image startPic;
    public Image startDuckPic;
    public Image playPic;

    public Player jared;
    public Player flyjared;
    public Player fluff;
    public Obstacle obstacle;
    public Obstacle[] bin;


    //Mouse position variables
    public int mouseX, mouseY;

    //button
    public Button button1;


    public boolean startTimer;

    public static void main(String[] args) {
        BasicGameApp myGame = new BasicGameApp();
        new Thread(myGame).start();
    }

    public BasicGameApp() {
        setUpGraphics();
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);

        button1 = new Button(300, 300, 150, 60);

        jaredPic = Toolkit.getDefaultToolkit().getImage("white duck.png");
        jared = new Player ("Jared", 700, 350, 300, 315, 0, 0);

        obstaclePic = Toolkit.getDefaultToolkit().getImage("recycle.png");
        obstacle = new Obstacle (300, 400, 1, 1,obstaclePic);

        flyjaredPic = Toolkit.getDefaultToolkit().getImage("birdflying-transformed.png");
        flyjared = new Player ("Flying Jared", 700, 350, 400, 325, 0,0);

//        fluffPic = Toolkit.getDefaultToolkit().getImage("whitefluff.png");
//        fluff = new Player ("Fluffy Jared", 700,350,200,215,0,0);

        backgroundPic = Toolkit.getDefaultToolkit().getImage("background.gif");
        startPic = Toolkit.getDefaultToolkit().getImage("startGIF.gif");
        startDuckPic = Toolkit.getDefaultToolkit().getImage("startDuck.gif");
        playPic = Toolkit.getDefaultToolkit().getImage("play button.png");


        //construct stinky array
        bin = new Obstacle[5];
        //fill stinky array with constructed cheese
        for(int x=0; x<bin.length; x++){
            bin[x] = new Obstacle(x*100+400, 400, 4, 2, obstaclePic);
        }
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
        jared.move();

        for (int x=0; x<bin.length; x++){
            bin[x].move();
        }
    }

    public void checkIntersections() {
        for (int x=0; x<bin.length; x++)
            if (bin[x].rec.intersects(jared.rec)){
                bin[x].isAlive = false;
            }
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
    public void keyPressed(KeyEvent event) {
        char key = event.getKeyChar();     //gets the character of the key pressed
        int keyCode = event.getKeyCode();  //gets the keyCode (an integer) of the key pressed
        System.out.println("Key Pressed: " + key + "  Code: " + keyCode);

        if (keyCode == 39) {//right arrow
            jared.right = true;
        }
        if (keyCode == 40) {//down arrow
            jared.down = true;
            jared.up = true;
        }
        if (keyCode == 37){//left arrow
            jared.left = true;
        }
        if (keyCode == 38){ //up arrow
            jared.up = true;
            jared.down = false;
        }

    }

    @Override
    public void keyReleased(KeyEvent event) {

        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();
        //This method will do something when a key is released
        if (keyCode == 37){//left arrow
            jared.left = false;
        }

        if (keyCode == 39) {//right arrow
            jared.right = false;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x, y;
        x = e.getX();
        y = e.getY();

        mouseX = x;
        mouseY = y;
        System.out.println();
        System.out.println("Mouse Clicked at " + x + ", " + y);

        if (button1.rec.contains(x, y)) {
            System.out.println("Game Started");

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println();
        System.out.println("Mouse Button Pressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println();
        System.out.println("Mouse Button Released");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println();
        System.out.println("Mouse has entered the window");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println();
        System.out.println("Mouse has left the window");
    }

    public void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //draw characters to the screen
        if (gameStart == false){
        g.drawImage(startPic, 0,0,WIDTH, HEIGHT, null);
        g.drawImage(startDuckPic, 100, 200, 200, 280, null);
        g.drawImage(playPic, 400, 300, 400, 400, null);
        }
        else {
            g.drawImage(backgroundPic, 0,0, WIDTH, HEIGHT, null);
            g.drawImage(obstaclePic, obstacle.xpos, obstacle.ypos, obstacle.width, obstacle.height, null);
            if (jared.down == true){
                g.drawImage(jaredPic, jared.xpos, 350, jared.width, jared.height, null);
            }else if (jared.up == true){
                g.drawImage(flyjaredPic, jared.xpos, 100, flyjared.width, flyjared.height, null);
            }

        }



//        g.drawImage(jaredPic, jared.xpos, jared.ypos, jared.width, jared.height, null);



        g.dispose();
        bufferStrategy.show();
    }

}

    //*******************************************************************************




