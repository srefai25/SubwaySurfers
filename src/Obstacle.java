import java.awt.*;

public class Obstacle {
    public String name;
    public int xpos;
    public int ypos;
    public int dx;
    public int dy;
    public int width;
    public int height;
    public Rectangle rec;

    public Obstacle(String pName, int pDx, int pDy, int pWidth, int pHeight){
        name = pName;
        xpos = 200;
        ypos = 100;
        dx = pDx;
        dy = pDy;
        width = pWidth;
        height = pHeight;
        rec = new Rectangle(xpos,ypos,width,height);

    }

    public void move(){
        xpos = 200;
        ypos = 100;
        for (int i=0;i<500;i++){
            xpos = xpos+dx;
            ypos = ypos+dy;
        }

    }
}
