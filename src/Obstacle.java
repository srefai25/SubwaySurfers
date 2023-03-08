import java.awt.*;

public class Obstacle {
    public double xpos;                //the x position
    public double ypos;                //the y position
    public int width;
    public int height;
    public boolean isAlive;            //a boolean to denote if the hero is alive or dead.
    public double dx;                    //the speed of the hero in the x direction
    public double dy;                    //the speed of the hero in the y direction
    public Rectangle rec;
    public Image pic;
    public int hits;

    // METHOD DEFINITION SECTION

    //This is a constructor that takes 3 parameters.  This allows us to specify the object's name and position when we build it.
    // if you put in a String, an int and an int the program will use this constructor instead of the one above.


    public Obstacle(int pXpos, int pYpos, int dxParameter, int dyParameter, Image picParameter) {

        xpos = pXpos;
        ypos = pYpos;
        width = 50;
        height = 50;
        dx = dxParameter;
        dy = dyParameter;
        pic = picParameter;
        isAlive = true;
        hits = 0;
        rec = new Rectangle((int)xpos, (int)ypos, width, height);


    } // constructor


    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {
        xpos = xpos + dx;
        ypos = ypos - dy;
        //width = (int)(width*.1);

        if (xpos > 1000 - width || xpos < 0) {
            dx = -dx;
        }

        if (ypos < 0 || ypos + height > 700) {
            dy = -dy;
        }

        rec = new Rectangle((int)xpos, (int)ypos, width, height);

    }


} //end of the Cheese object class  definition