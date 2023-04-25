import java.awt.*;

public class Obstacle {
    public double xpos;                //the x position
    public double ypos;                //the y position
    public double width;
    public double height;
    public boolean isAlive;            //a boolean to denote if the hero is alive or dead.
    public double dx;                    //the speed of the hero in the x direction
    public double dy;                    //the speed of the hero in the y direction
    public Rectangle rec;
    public Image pic;
    public int hits;
    public int toDot;
    public Point[] path;

    // METHOD DEFINITION SECTION

    //This is a constructor that takes 3 parameters.  This allows us to specify the object's name and position when we build it.
    // if you put in a String, an int and an int the program will use this constructor instead of the one above.


    public Obstacle(int pXpos, int pYpos, int dxParameter, int dyParameter, Image picParameter, Point[] pathParameter) {

        xpos = pXpos;
        ypos = pYpos;
        width = 50;
        height = 50;
        dx = dxParameter;
        dy = dyParameter;
        pic = picParameter;
        isAlive = false;
        hits = 0;
        toDot = 0;
        path = pathParameter;
        rec = new Rectangle((int)xpos, (int)ypos, (int)width, (int)height);


    } // constructor


    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {
        xpos = xpos + dx;
        ypos = ypos + dy;
        //width = (int)(width*.1);

        if (xpos > 1500 - width || xpos < 0) {
            dx = -dx;
        }

        if (ypos < 0 || ypos + height > 900) {
            dy = -dy;
            isAlive = false;
            xpos = 123;
            ypos = 464;
        }

        rec = new Rectangle((int)xpos, (int)ypos, (int)width, (int)height);

    }

    public void random(){


    }


} //end of the Cheese object class  definition