import java.awt.*;

public class Jake {

    public String name;
    public int xpos;
    public int ypos;
    public int dx;
    public int dy;
    public int width;
    public int height;
    public Rectangle rec;
    public boolean right;
    public boolean down;
    public boolean left;
    public boolean up;
    public Rectangle rec;			//declare a rectangle variable
    public String text;

    public Jake(String pName, int pXpos, int pYpos, int pWidth, int pHeight, int pDx, int pDy, boolean pRight, boolean pLeft, boolean pUp, boolean pDown){
        name = pName;
        xpos = pXpos;
        ypos = pYpos;
        dx = pDx;
        dy = pDy;
        width = pWidth;
        height = pHeight;
        right = pRight;
        left = pLeft;
        up = pUp;
        down = pDown;
        rec = new Rectangle(xpos,ypos,width,height);
    }

    public void move(){
        xpos = xpos+dx;
        ypos = ypos+dy;
    }
}
