import java.awt.*;

public class Player {

    public String name;
    public int xpos;
    public int ypos;
    public int dx;
    public int dy;
    public int width;
    public int height;
    public Image pic;
    public Rectangle rec;
    public boolean right;
    public boolean down = true;
    public boolean left;
    public boolean up;
//    public boolean regular;//original state of duck
    //declare a rectangle variable
    public String text;

    public Player(String pName, int pXpos, int pYpos, int pWidth, int pHeight, int pDx, int pDy){
        name = pName;
        xpos = pXpos;
        ypos = pYpos;
        width = pWidth;
        height = pHeight;
        dx = pDx;
        dy = pDy;
        rec = new Rectangle(xpos,ypos,width,height);
    }

    public void move(){
        xpos = xpos + dx;
        ypos = ypos + dy;

        if (right == true){
            dx = 7;
        } else if (left == true){
            dx = -7;
        } else {//(left == false && right == false)
            dx = 0;
        }


        if (xpos<0) {//left wall
            xpos = 0;
        }
        if (xpos>1000-width){//right wall
            xpos = 1000-width;
        }
        if (ypos<0){//up wall
            ypos = 0;
        }
        if (ypos >700-height){//down wall
            ypos = 700-height;
        }
        //always put this after you've done all the changing of the xpos and ypos values
        rec = new Rectangle(xpos, ypos, width, height);
        }


}
