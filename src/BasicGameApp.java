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
    public Point[] dots;


    //Mouse position variables
    public int mouseX, mouseY;

    //button
    public Button button1;
    public double mag=2;
    public double changeX,changeY,totalDistance;

    public boolean startTimer;

    public static void main(String[] args) {
        BasicGameApp myGame = new BasicGameApp();
        new Thread(myGame).start();
    }

    public BasicGameApp() {
        setUpGraphics();
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);

        button1 = new Button(400, 400, 400, 150);

        jaredPic = Toolkit.getDefaultToolkit().getImage("white duck.png");
        jared = new Player ("Jared", 700, 350, 300, 315, 0, 0);

        obstaclePic = Toolkit.getDefaultToolkit().getImage("recycle.png");
//        obstacle = new Obstacle (200, 400, 1, 1,obstaclePic);

        flyjaredPic = Toolkit.getDefaultToolkit().getImage("birdflying-transformed.png");
        flyjared = new Player ("Flying Jared", 700, 350, 400, 325, 0,0);

//        fluffPic = Toolkit.getDefaultToolkit().getImage("whitefluff.png");
//        fluff = new Player ("Fluffy Jared", 700,350,200,215,0,0);

        backgroundPic = Toolkit.getDefaultToolkit().getImage("background.gif");
        startPic = Toolkit.getDefaultToolkit().getImage("startGIF.gif");
        startDuckPic = Toolkit.getDefaultToolkit().getImage("startDuck.gif");
        playPic = Toolkit.getDefaultToolkit().getImage("play button.png");
        dots = new Point [16];
        dots[0] = new Point (123, 464);
        dots[1] = new Point (230, 449);
        dots[2] = new Point (229, 433);
        dots[3] = new Point (236, 418);
        dots[4] = new Point (249, 408);
        dots[5] = new Point (272, 404);
        dots[6] = new Point (291, 409);
        dots[7] = new Point (303, 417);
        dots[8] = new Point (306, 430);
        dots[9] = new Point (282, 464);
        dots[10] = new Point (222, 469);
        dots[11] = new Point (180, 419);
        dots[12] = new Point (189, 339);
        dots[13] = new Point (226, 301);
        dots[14] = new Point (560, 373);
        dots[15] = new Point (572, 394);


        //construct stinky array
        bin = new Obstacle[1];


        //fill stinky array with constructed bins
        for(int x=0; x<bin.length; x++){
            bin[x] = new Obstacle(200, 100, 1, 0, obstaclePic);

            changeY = dots[bin[x].toDot].ypos - bin[x].ypos;
            changeX = dots[bin[x].toDot].xpos - bin[x].xpos;

            totalDistance = Math.sqrt(Math.pow(changeX, 2) + Math.pow(changeY, 2));
            System.out.println("changeX" + changeX);
            System.out.println("changeY" + changeY);
            System.out.println("total" + totalDistance);

            bin[x].dy = mag * (changeY / totalDistance);
            bin[x].dx = mag * (changeX / totalDistance);
        }
    }

    public void run() {
        while (true) {
            moveThings();
            checkIntersections();
            render();
            pause(20);
            binPosition();
        }
    }

    public void binPosition(){
        for (int i=0; i<bin.length; i++){
            for (int x=0; x<dots.length; x++) {
                if (bin[i].rec.intersects(dots[x].rec)) {
                    System.out.println("bin is intersecting with dot" + x);
                }
            }
        }

    }
    public void moveThings() {
        setDirection();

        jared.move();

        for (int x=0; x<bin.length; x++){
            bin[x].move();
        }
    }

    public void checkIntersections() {
        for (int x=0; x<bin.length; x++)
            if (bin[x].rec.intersects(jared.rec)){
                jared.isAlive = false;
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
//        canvas.addMouseListener(this);
//        canvas.addKeyListener(this);
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
            gameStart = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        System.out.println();
//        System.out.println("Mouse Button Pressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        System.out.println();
//        System.out.println("Mouse Button Released");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        System.out.println();
//        System.out.println("Mouse has entered the window");
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        System.out.println();
//        System.out.println("Mouse has left the window");
    }
    public void setDirection(){
        if(bin[0].toDot==dots.length){
            bin[0].dy = 1;
            bin[0].dx = 0;
        }
        else {
        if(bin[0].rec.intersects(dots[bin[0].toDot].rec)) {
            bin[0].toDot++;
            if(bin[0].toDot==dots.length){
                bin[0].dy = 1;
                bin[0].dx = 0;
            }
            else {
                changeY = dots[bin[0].toDot].ypos - bin[0].ypos;
                changeX = dots[bin[0].toDot].xpos - bin[0].xpos;

                totalDistance = Math.sqrt(Math.pow(changeX, 2) + Math.pow(changeY, 2));
                System.out.println("changeX" + changeX);
                System.out.println("changeY" + changeY);
                System.out.println("total" + totalDistance);

                bin[0].dy = mag * (changeY / totalDistance);
                bin[0].dx = mag * (changeX / totalDistance);
            }
            }
        }


    }

    public void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        //draw characters to the screen
        if (gameStart == false){
        g.drawImage(startPic, 0,0,WIDTH, HEIGHT, null);
        g.drawImage(startDuckPic, 100, 200, 200, 280, null);
        g.drawImage(playPic, button1.xpos, button1.ypos, button1.width, button1.height, null);
        g.drawRect(button1.rec.x, button1.rec.y, button1.rec.width, button1.rec.height);
        }
        else {
            g.drawImage(backgroundPic, 0,0, WIDTH, HEIGHT, null);
            for (int x=0; x< bin.length;x++){
                if (jared.isAlive == true) {
                  //  System.out.println(bin[x].height + " w: " + bin[x].width);
                    g.drawImage(bin[x].pic,(int) bin[x].xpos, (int)bin[x].ypos, bin[x].width, bin[x].height, null);
                }
            }
            if (jared.down == true){
                g.drawImage(jaredPic, jared.xpos, 350, jared.width, jared.height, null);
            }else if (jared.up == true){
                g.drawImage(flyjaredPic, jared.xpos, 100, flyjared.width, flyjared.height, null);
            }

        }



//        g.drawImage(jaredPic, jared.xpos, jared.ypos, jared.width, jared.height, null);


        g.setColor(Color.yellow);
        g.drawRect(dots[1].rec.x, dots[1].rec.y, 10,10);

        g.dispose();
        bufferStrategy.show();
    }

}

    //*******************************************************************************




