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
    public boolean gameover = false;
    public boolean Tryagain = false;

    public Image jaredPic;
    public Image obstaclePic;
    public Image flyjaredPic;
    public Image fluffPic;
    public Image backgroundPic;
    public Image startPic;
    public Image startDuckPic;
    public Image playPic;
    public Image gameoverPic;

    public Player jared;
    public Player flyjared;
    public Player fluff;
    public Obstacle obstacle;
    public Obstacle[] bin;
    public Point[] path1;
    public Point[] path2;
    public Point[] path3;


    //Mouse position variables
    public int mouseX, mouseY;

    //button
    public Button button1;
    public Button gameOver;
    public Button TryAgain;
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
        jared = new Player ("Jared", 700, 600, 125, 200, 0, 0);

        obstaclePic = Toolkit.getDefaultToolkit().getImage("recycle.png");
//        obstacle = new Obstacle (200, 400, 1, 1,obstaclePic);

//        flyjaredPic = Toolkit.getDefaultToolkit().getImage("birdflying-transformed.png");
//        flyjared = new Player ("Flying Jared", 700, 350, 400, 325, 0,0);

        fluffPic = Toolkit.getDefaultToolkit().getImage("whitefluff.png");
        fluff = new Player ("Fluffy Jared", 700,350,200,215,0,0);
        gameOver = new Button(0, 0, 1000, 700);
        TryAgain = new Button(300, 420, 200, 100);

        backgroundPic = Toolkit.getDefaultToolkit().getImage("background.gif");
        startPic = Toolkit.getDefaultToolkit().getImage("startGIF.gif");
        gameoverPic = Toolkit.getDefaultToolkit().getImage("GAMEOVER.gif");
        startDuckPic = Toolkit.getDefaultToolkit().getImage("startDuck.gif");
        playPic = Toolkit.getDefaultToolkit().getImage("play button.png");
        path1 = new Point [14];
        path1[0] = new Point (220, 445);
        path1[1] = new Point (236, 350);
        path1[2] = new Point (272, 360);
        path1[3] = new Point (313, 317);
        path1[4] = new Point (222, 469);
        path1[5] = new Point (100, 280);
        path1[6] = new Point (180, 219);
        path1[7] = new Point (189, 139);
        path1[8] = new Point (226, 101);
        path1[9] = new Point (360, 175);
        path1[10] = new Point (400, 150);
        path1[11] = new Point (550, 250);
        path1[12] = new Point (650, 300);
        path1[13] = new Point (760, 350);

        path2 = new Point [14];
        path2[0] = new Point (220, 445);
        path2[1] = new Point (236, 350);
        path2[2] = new Point (272, 360);
        path2[3] = new Point (313, 317);
        path2[4] = new Point (222, 469);
        path2[5] = new Point (100, 280);
        path2[6] = new Point (180, 219);
        path2[7] = new Point (189, 139);
        path2[8] = new Point (226, 101);
        path2[9] = new Point (360, 175);
        path2[10] = new Point (400, 150);
        path2[11] = new Point (700, 250);
        path2[12] = new Point (720, 170);
        path2[13] = new Point (820, 200);

        path3 = new Point [14];
        path3[0] = new Point (220, 445);
        path3[1] = new Point (236, 350);
        path3[2] = new Point (272, 360);
        path3[3] = new Point (313, 317);
        path3[4] = new Point (222, 469);
        path3[5] = new Point (100, 280);
        path3[6] = new Point (180, 219);
        path3[7] = new Point (189, 139);
        path3[8] = new Point (226, 101);
        path3[9] = new Point (360, 175);
        path3[10] = new Point (400, 150);
        path3[11] = new Point (470, 300);
        path3[12] = new Point (440, 500);
        path3[13] = new Point (420, 700);




//        int placement = (int)(Math.random()*3);


        //construct bin array
            bin = new Obstacle[20];


        //fill bin array with constructed bins
        for(int x=0; x<bin.length; x++){

            int placement = (int)(Math.random()*3);
            if (placement == 0) {
                bin[x] = new Obstacle(123, 464, 3, 0, obstaclePic, path1);
            }
            else if (placement == 1) {
                bin[x] = new Obstacle(123, 464, 3, 0, obstaclePic, path2);
            }
            else if (placement == 2){
                bin[x] = new Obstacle(123, 464, 3, 0, obstaclePic, path3);
            }
            System.out.println("bin placed on path " + placement);

//            need to find a way to stop getting three rows of bins

            changeY = bin[x].path[bin[x].toDot].ypos - bin[x].ypos;
            changeX = bin[x].path[bin[x].toDot].xpos - bin[x].xpos;

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
            triggerBins();
        }
    }

    public void binPosition(){//when bin is intersecting with dot
        for (int i=0; i<bin.length; i++){
            for (int x = 0; x< bin[i].path.length; x++) {
                if (bin[i].rec.intersects(bin[i].path[x].rec)) {
//                    System.out.println("bin is intersecting with dot" + x);
                }
            }
        }

    }

    public void triggerBins() {
        for (int i = 0; i < bin.length; i++) {
            if (bin[i].isAlive == false) {
                double r = Math.random();
                if (r > 0.9999) {
                    bin[i].isAlive = true;
                }
            }
        }
        for (int i = 0; i < bin.length; i++) {
            System.out.println("bin " + i + " isAlive " + bin[i].isAlive);
        }
    }

    public void moveThings() {//moving constructer
        setDirection();

        jared.move();

        for (int x=0; x<bin.length; x++){
            if (bin[x].isAlive == true){
                bin[x].move();
            }
        }
    }

    public void checkIntersections() {//when jared dies
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
    }//pause method


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


    }//graphics

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

        if (gameOver.rec.contains(x, y)) {
            System.out.println("Game Over");
            gameover = true;
        }

        if (TryAgain.rec.contains(x, y)) {
            System.out.println("Game Over");
            Tryagain = true;
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
        for (int x=0; x<bin.length; x++){
            if(bin[x].toDot == bin[x].path.length){
                bin[x].dy = 1;
                bin[x].dx = 0;
            }
            else {
                if(bin[x].rec.intersects(bin[x].path[bin[x].toDot].rec)) {
                    bin[x].toDot++;
                    bin[x].width = bin[x].width*1.1;
                    bin[x].height = bin[x].height*1.1;
                    if(bin[x].toDot== bin[x].path.length){
                        bin[x].dy = 1;
                        bin[x].dx = 0;
                    }
                    else {
                        changeY = bin[x].path[bin[x].toDot].ypos - bin[x].ypos;
                        changeX = bin[x].path[bin[x].toDot].xpos - bin[x].xpos;

                        totalDistance = Math.sqrt(Math.pow(changeX, 2) + Math.pow(changeY, 2));
                        System.out.println("changeX" + changeX);
                        System.out.println("changeY" + changeY);
                        System.out.println("total" + totalDistance);

                        bin[x].dy = mag * (changeY / totalDistance);
                        bin[x].dx = mag * (changeX / totalDistance);
                    }
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
                if (bin[x].isAlive == true) {
                    g.drawImage(bin[x].pic, (int) bin[x].xpos, (int) bin[x].ypos, (int) bin[x].width, (int) bin[x].height, null);
                }
                if (jared.isAlive == true) {
                  //  System.out.println(bin[x].height + " w: " + bin[x].width);
                    if (jared.down == true){
                        g.drawImage(jaredPic, jared.xpos, 350, jared.width, jared.height, null);
                    }else if (jared.up == true){
                        g.drawImage(flyjaredPic, jared.xpos, 100, flyjared.width, flyjared.height, null);
                    }
                }
            }



//            if (Tryagain = true){
//                gameStart = false;
//            }
//            else{
//                gameStart = true;
//            }
//            g.drawRect(jared.rec.x,jared.rec.y,jared.rec.width,jared.rec.height);

        }



//        g.drawImage(jaredPic, jared.xpos, jared.ypos, jared.width, jared.height, null);


//        g.setColor(Color.yellow);
//        for (int i = 0; i < bin[0].path.length; i++) {
//            g.drawRect(bin[0].path[i].rec.x, bin[0].path[i].rec.y, 10, 10);
//            g.drawString(String.valueOf(i), bin[0].path[i].rec.x, bin[0].path[i].rec.y);
//        }

        g.dispose();
        bufferStrategy.show();
    }

}

//*******************************************************************************




