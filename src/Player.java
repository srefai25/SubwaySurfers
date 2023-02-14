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
    public boolean down;
    public boolean left;
    public boolean up;
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
        xpos = xpos+dx;
        ypos = ypos+dy;
    }
}
